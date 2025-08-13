package smartPark.smart_park.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.LoginRequest;
import smartPark.smart_park.models.dto.request.RefreshTokenRequest;
import smartPark.smart_park.models.dto.response.AuthResponse;
import smartPark.smart_park.services.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
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
        // Cette méthode peut être utilisée pour vérifier si l'utilisateur est connecté
        // et récupérer ses informations de base
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Utilisateur authentifié");
        response.put("authenticated", true);

        return ResponseEntity.ok(response);
    }
}