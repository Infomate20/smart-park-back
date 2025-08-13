package smartPark.smart_park.services;

import smartPark.smart_park.models.dto.request.LoginRequest;
import smartPark.smart_park.models.dto.request.RefreshTokenRequest;
import smartPark.smart_park.models.dto.response.AuthResponse;

public interface AuthService {

    /**
     * Authentifie un utilisateur et retourne les tokens JWT
     */
    AuthResponse login(LoginRequest request);

    /**
     * Renouvelle l'access token à partir du refresh token
     */
    AuthResponse refreshToken(RefreshTokenRequest request);

    /**
     * Déconnecte un utilisateur (invalide le token)
     */
    void logout(String token);
}