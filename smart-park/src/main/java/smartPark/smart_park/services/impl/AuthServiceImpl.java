package smartPark.smart_park.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.dto.request.LoginRequest;
import smartPark.smart_park.models.dto.request.RefreshTokenRequest;
import smartPark.smart_park.models.dto.response.AuthResponse;
import smartPark.smart_park.exceptions.InvalidTokenException;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.repository.UtilisateurRepository;
import smartPark.smart_park.security.CustomUserDetailsService;
import smartPark.smart_park.security.JwtService;
import smartPark.smart_park.services.AuthService;
import smartPark.smart_park.services.OtpService;
import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.models.dto.request.ReinitialisationMotDePasseRequestDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private static final int MAX_FAILED_ATTEMPTS = 5;

    @Override
    @Transactional
    public Long authenticateAndGetUserId(String usernameOrEmailOrPhone, String password) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // 2. On applique votre logique de sécurité existante
        if (utilisateur.getCompteVerrouille()) {
            throw new LockedException("Compte verrouillé suite à trop de tentatives de connexion échouées.");
        }
        if (!utilisateur.getActif()) {
            throw new DisabledException("Compte désactivé.");
        }

        try {
            // 3. On utilise l'AuthenticationManager pour valider le mot de passe.
            // Il utilisera implicitement votre CustomUserDetailsService.
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usernameOrEmailOrPhone, password)
            );

            // 4. Si l'authentification réussit, on réinitialise les tentatives échouées
            if (utilisateur.getTentativesConnexionEchouees() > 0) {
                utilisateur.setTentativesConnexionEchouees(0);
                utilisateurRepository.save(utilisateur);
            }

            // 5. On retourne l'ID pour l'étape suivante du 2FA
            return utilisateur.getId();

        } catch (BadCredentialsException e) {
            // En cas d'échec, on applique votre logique de verrouillage de compte
            int tentatives = utilisateur.getTentativesConnexionEchouees() + 1;
            utilisateur.setTentativesConnexionEchouees(tentatives);

            if (tentatives >= MAX_FAILED_ATTEMPTS) {
                utilisateur.setCompteVerrouille(true);
            }
            utilisateurRepository.save(utilisateur);

            // On relance l'exception pour que le contrôleur renvoie une erreur 401
            throw new BadCredentialsException("Identifiant ou mot de passe incorrect.");
        }
    }

    @Override
    @Transactional
    public AuthResponse generateTokenForUser(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + userId));

        // On applique votre logique de mise à jour post-connexion
        utilisateur.setDerniereConnexion(LocalDateTime.now());
        if (utilisateur.getPremiereConnexion()) {
            utilisateur.setPremiereConnexion(false);
        }
        utilisateurRepository.save(utilisateur);

        // On génère les tokens en utilisant votre JwtService
        String accessToken = jwtService.generateToken(utilisateur);
        String refreshToken = jwtService.generateRefreshToken(utilisateur);

        // On construit la réponse en utilisant votre AuthResponse.builder()
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(utilisateur.getId()) // Assurez-vous que votre DTO a bien un champ 'id' et non 'userId'
                .nom(utilisateur.getNomUtilisateur()) // et 'nom' et non 'nomUtilisateur'
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .role(utilisateur.getRole())
                .premiereConnexion(utilisateur.getPremiereConnexion())
                .build();
    }


    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        try {
            String nomUtilisateur = jwtService.extractUsername(refreshToken);

            if (nomUtilisateur != null) {
                var userDetails = userDetailsService.loadUserByUsername(nomUtilisateur);

                if (jwtService.isTokenValid(refreshToken, userDetails)) {
                    Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur)
                            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

                    String newAccessToken = jwtService.generateToken(utilisateur);
                    String newRefreshToken = jwtService.generateRefreshToken(utilisateur);

                    return AuthResponse.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(newRefreshToken)
                            .userId(utilisateur.getId())
                            .nomUtilisateur(utilisateur.getNomUtilisateur())
                            .prenom(utilisateur.getPrenom())
                            .email(utilisateur.getEmail())
                            .role(utilisateur.getRole())
                            .premiereConnexion(utilisateur.getPremiereConnexion())
                            .build();
                }
            }
        } catch (Exception e) {
            throw new InvalidTokenException("Refresh token invalide ou expiré");
        }

        throw new InvalidTokenException("Refresh token invalide");
    }

    @Override
    public void logout(String token) {
        // Dans une implémentation complète, vous pourriez vouloir:
        // 1. Ajouter le token à une blacklist
        // 2. Stocker les tokens révoqués en base de données
        // 3. Utiliser Redis pour une blacklist en cache

        // Pour cette implémentation basique, nous nous contentons de valider le token
        try {
            String nomUtilisateur = jwtService.extractUsername(token);
            if (nomUtilisateur == null) {
                throw new InvalidTokenException("Token invalide");
            }
        } catch (Exception e) {
            throw new InvalidTokenException("Token invalide pour la déconnexion");
        }

    }

    // =========================================================================
    // Réinitialisation de mot de passe (mot de passe oublié)
    //
    // Le code à usage unique n'intervient plus à la connexion : il imposait un
    // serveur SMTP joignable à chaque ouverture de session. Il est désormais
    // réservé à ce parcours, où l'envoi d'un e-mail est inhérent au besoin.
    // =========================================================================

    @Override
    public void demanderReinitialisationMotDePasse(String email) {
        utilisateurRepository.findByEmail(email).ifPresentOrElse(
                utilisateur -> {
                    otpService.generateAndSendOtp(utilisateur.getId());
                    log.info("Code de réinitialisation envoyé pour l'utilisateur {}", utilisateur.getId());
                },
                // Adresse inconnue : on ne fait rien, et l'appelant reçoit la
                // même réponse. Distinguer les deux cas permettrait d'énumérer
                // les comptes existants.
                () -> log.info("Demande de réinitialisation pour une adresse inconnue, ignorée")
        );
    }

    @Override
    public void reinitialiserMotDePasse(ReinitialisationMotDePasseRequestDto request) {
        if (!request.getNouveauMotDePasse().equals(request.getConfirmationMotDePasse())) {
            throw new BusinessException("La confirmation du mot de passe ne correspond pas");
        }

        // Adresse inconnue et code erroné renvoient volontairement le même
        // message : les distinguer révélerait quels comptes existent.
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Code de vérification invalide ou expiré"));

        if (!otpService.validateOtp(utilisateur.getId(), request.getCode())) {
            throw new BusinessException("Code de vérification invalide ou expiré");
        }

        utilisateur.setMotDePasse(passwordEncoder.encode(request.getNouveauMotDePasse()));
        utilisateur.setMotDePasseExpire(false);

        // La réinitialisation est la voie de retour d'un compte verrouillé après
        // cinq échecs : le laisser verrouillé rendrait le parcours inopérant.
        utilisateur.setCompteVerrouille(false);
        utilisateur.setTentativesConnexionEchouees(0);

        utilisateurRepository.save(utilisateur);
        log.info("Mot de passe réinitialisé pour l'utilisateur {}", utilisateur.getId());
    }
}