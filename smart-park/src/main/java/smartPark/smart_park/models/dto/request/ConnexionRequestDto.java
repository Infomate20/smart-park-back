package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnexionRequestDto {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String nomUtilisateur;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}