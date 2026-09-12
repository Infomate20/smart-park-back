package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Demande d'envoi d'un code de réinitialisation de mot de passe.
 *
 * <p>La réponse est volontairement identique que l'adresse existe ou non :
 * distinguer les deux cas transformerait ce formulaire en outil d'énumération
 * des comptes.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotDePasseOublieRequestDto {

    @NotBlank(message = "L'adresse e-mail est obligatoire")
    @Email(message = "L'adresse e-mail est invalide")
    private String email;
}
