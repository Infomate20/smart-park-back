package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Réinitialisation d'un mot de passe oublié.
 *
 * <p>Le code à usage unique est vérifié et le mot de passe appliqué dans le
 * même appel : découper en deux exigerait un jeton intermédiaire à faire vivre
 * côté client, pour aucun gain.</p>
 *
 * <p>Les contraintes de robustesse sont identiques à celles de
 * {@link ChangementMotDePasseRequestDto}, afin qu'un mot de passe accepté ici
 * le soit également lors d'un changement ultérieur.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReinitialisationMotDePasseRequestDto {

    @NotBlank(message = "L'adresse e-mail est obligatoire")
    @Email(message = "L'adresse e-mail est invalide")
    private String email;

    @NotBlank(message = "Le code de vérification est obligatoire")
    private String code;

    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 8, max = 255, message = "Le nouveau mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Le nouveau mot de passe doit contenir au moins une minuscule, une majuscule et un chiffre")
    private String nouveauMotDePasse;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmationMotDePasse;
}
