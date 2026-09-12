package smartPark.smart_park.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.LoginRequest;
import smartPark.smart_park.models.dto.request.MotDePasseOublieRequestDto;
import smartPark.smart_park.models.dto.request.OtpRequestDto;
import smartPark.smart_park.models.dto.request.OtpValidationRequestDto;
import smartPark.smart_park.models.dto.request.RefreshTokenRequest;
import smartPark.smart_park.models.dto.request.ReinitialisationMotDePasseRequestDto;
import smartPark.smart_park.models.dto.response.AuthResponse;
import smartPark.smart_park.services.AuthService;
import smartPark.smart_park.services.OtpService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    /**
     * Authentifie l'utilisateur et ouvre la session en un seul appel.
     *
     * <p>La vérification en deux temps par code à usage unique a été retirée de
     * la connexion : elle imposait un serveur SMTP joignable à chaque ouverture
     * de session, ce qui est disproportionné pour un déploiement local. L'OTP
     * est désormais réservé à la réinitialisation de mot de passe.</p>
     *
     * <p>Les contrôles de compte verrouillé, de compte désactivé et de remise à
     * zéro des tentatives échouées restent assurés par
     * {@code authenticateAndGetUserId}.</p>
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Long userId = authService.authenticateAndGetUserId(request.getIdentifier(), request.getPassword());
        return ResponseEntity.ok(authService.generateTokenForUser(userId));
    }

    /**
     * Demande l'envoi d'un code de réinitialisation.
     *
     * <p>Répond 200 même si l'adresse est inconnue : une réponse différenciée
     * transformerait cet endpoint en outil d'énumération des comptes.</p>
     */
    @PostMapping("/mot-de-passe-oublie")
    public ResponseEntity<Map<String, String>> motDePasseOublie(
            @Valid @RequestBody MotDePasseOublieRequestDto request) {
        authService.demanderReinitialisationMotDePasse(request.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Si un compte existe pour cette adresse, un code de vérification vient d'y être envoyé.");
        return ResponseEntity.ok(response);
    }

    /**
     * Vérifie le code et applique le nouveau mot de passe.
     * N'ouvre aucune session : l'utilisateur se reconnecte ensuite.
     */
    @PostMapping("/reinitialiser-mot-de-passe")
    public ResponseEntity<Map<String, String>> reinitialiserMotDePasse(
            @Valid @RequestBody ReinitialisationMotDePasseRequestDto request) {
        authService.reinitialiserMotDePasse(request);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mot de passe réinitialisé. Vous pouvez vous connecter.");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtpAndLogin(@Valid @RequestBody OtpValidationRequestDto validationRequest) {
        boolean isOtpValid = otpService.validateOtp(validationRequest.getUserId(), validationRequest.getCode());

        if (!isOtpValid) {
            throw new BadCredentialsException("Code de vérification invalide ou expiré.");
        }

        AuthResponse authResponse = authService.generateTokenForUser(validationRequest.getUserId());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(@Valid @RequestBody OtpRequestDto otpRequest) {
        otpService.generateAndSendOtp(otpRequest.getUserId());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Code de vérification envoyé à votre adresse email.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authService.logout(token);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Déconnexion réussie");

        return ResponseEntity.ok(response);
    }
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Utilisateur authentifié");
        response.put("authenticated", true);

        return ResponseEntity.ok(response);
    }
}