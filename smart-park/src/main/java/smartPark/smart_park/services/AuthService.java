package smartPark.smart_park.services;

import smartPark.smart_park.models.dto.request.LoginRequest;
import smartPark.smart_park.models.dto.request.RefreshTokenRequest;
import smartPark.smart_park.models.dto.request.ReinitialisationMotDePasseRequestDto;
import smartPark.smart_park.models.dto.response.AuthResponse;

public interface AuthService {

    /**
     * Authentifie un utilisateur et retourne les tokens JWT
     */
    //AuthResponse login(LoginRequest request);

    /**
     * Renouvelle l'access token à partir du refresh token
     */
    AuthResponse refreshToken(RefreshTokenRequest request);

    /**
     * Déconnecte un utilisateur (invalide le token)
     */
    void logout(String token);

    /**
     * Authentifie un utilisateur avec son login/téléphone/email et mot de passe.
     * Si l'authentification réussit, retourne l'ID de l'utilisateur.
     * Si elle échoue, lève une exception (gérée par Spring Security).
     * @param username Le login, email ou téléphone fourni.
     * @param password Le mot de passe fourni.
     * @return L'ID de l'utilisateur authentifié.
     */
    Long authenticateAndGetUserId(String username, String password);

    /**
     * Génère une réponse d'authentification complète (avec tokens) pour un utilisateur
     * dont l'identité a déjà été vérifiée (par l'OTP).
     * @param userId L'ID de l'utilisateur pour lequel générer le token.
     * @return Un AuthResponse contenant le JWT.
     */
    AuthResponse generateTokenForUser(Long userId);

    /**
     * Envoie un code de réinitialisation à l'adresse indiquée.
     *
     * <p>Ne lève aucune exception si l'adresse est inconnue : la réponse doit
     * être identique dans les deux cas, faute de quoi l'endpoint permettrait
     * d'énumérer les comptes existants.</p>
     *
     * @param email L'adresse pour laquelle un code est demandé.
     */
    void demanderReinitialisationMotDePasse(String email);

    /**
     * Vérifie le code à usage unique et applique le nouveau mot de passe.
     *
     * <p>N'ouvre aucune session : l'utilisateur se reconnecte ensuite
     * normalement.</p>
     *
     * @param request L'adresse, le code reçu et le nouveau mot de passe.
     */
    void reinitialiserMotDePasse(ReinitialisationMotDePasseRequestDto request);
}