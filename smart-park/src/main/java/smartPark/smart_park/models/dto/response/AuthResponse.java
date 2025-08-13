package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String nomUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
    private boolean premiereConnexion;
}