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
import smartPark.smart_park.models.dto.request.LoginRequest;
import smartPark.smart_park.models.dto.request.RefreshTokenRequest;
import smartPark.smart_park.models.dto.response.AuthResponse;
import smartPark.smart_park.exceptions.InvalidTokenException;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.repository.UtilisateurRepository;
import smartPark.smart_park.security.CustomUserDetailsService;
import smartPark.smart_park.security.JwtService;
import smartPark.smart_park.services.AuthService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private static final int MAX_FAILED_ATTEMPTS = 5;

    @Override
    public AuthResponse login(LoginRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(request.getNomUtilisateur())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Vérifier si le compte est verrouillé
        if (utilisateur.getCompteVerrouille()) {
            throw new LockedException("Compte verrouillé suite à trop de tentatives de connexion échouées");
        }

        // Vérifier si le compte est actif
        if (!utilisateur.getActif()) {
            throw new DisabledException("Compte désactivé");
        }

        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNomUtilisateur(),
                            request.getMotDePasse()
                    )
            );

            // Réinitialiser les tentatives échouées en cas de succès
            if (utilisateur.getTentativesConnexionEchouees() > 0) {
                utilisateur.setTentativesConnexionEchouees(0);
            }

            // Mettre à jour la dernière connexion
            utilisateur.setDerniereConnexion(LocalDateTime.now());

            // Marquer que ce n'est plus la première connexion
            if (utilisateur.getPremiereConnexion()) {
                utilisateur.setPremiereConnexion(false);
            }

            utilisateurRepository.save(utilisateur);

            // Générer les tokens
            String accessToken = jwtService.generateToken(utilisateur);
            String refreshToken = jwtService.generateRefreshToken(utilisateur);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .userId(utilisateur.getId())
                    .nomUtilisateur(utilisateur.getNomUtilisateur())
                    .prenom(utilisateur.getPrenom())
                    .email(utilisateur.getEmail())
                    .role(utilisateur.getRole())
                    .premiereConnexion(utilisateur.getPremiereConnexion())
                    .build();

        } catch (BadCredentialsException e) {
            // Incrémenter les tentatives échouées
            int tentatives = utilisateur.getTentativesConnexionEchouees() + 1;
            utilisateur.setTentativesConnexionEchouees(tentatives);

            // Verrouiller le compte si trop de tentatives
            if (tentatives >= MAX_FAILED_ATTEMPTS) {
                utilisateur.setCompteVerrouille(true);
            }

            utilisateurRepository.save(utilisateur);

            throw new BadCredentialsException("Nom d'utilisateur ou mot de passe incorrect");
        }
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
}