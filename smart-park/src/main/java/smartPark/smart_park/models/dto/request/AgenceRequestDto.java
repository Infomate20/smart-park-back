package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgenceRequestDto {

    @NotBlank(message = "Le code de l'agence est obligatoire")
    @Size(min = 2, max = 10, message = "Le code doit contenir entre 2 et 10 caractères")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Le code ne peut contenir que des lettres majuscules et des chiffres")
    private String code;

    @NotBlank(message = "Le nom de l'agence est obligatoire")
    @Size(min = 2, max = 200, message = "Le nom doit contenir entre 2 et 200 caractères")
    private String nom;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @Size(max = 200, message = "L'adresse ne peut pas dépasser 200 caractères")
    private String adresse;

    @Size(max = 100, message = "La ville ne peut pas dépasser 100 caractères")
    private String ville;

    @Size(max = 10, message = "Le code postal ne peut pas dépasser 10 caractères")
    @Pattern(regexp = "^[0-9]*$", message = "Le code postal ne peut contenir que des chiffres")
    private String codePostal;

    @Size(max = 100, message = "Le pays ne peut pas dépasser 100 caractères")
    private String pays;

    @Size(max = 20, message = "Le téléphone ne peut pas dépasser 20 caractères")
    @Pattern(regexp = "^[0-9+\\-\\s]*$", message = "Le téléphone contient des caractères invalides")
    private String telephone;

    @Size(max = 20, message = "Le fax ne peut pas dépasser 20 caractères")
    @Pattern(regexp = "^[0-9+\\-\\s]*$", message = "Le fax contient des caractères invalides")
    private String fax;

    @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
    @Email(message = "Format d'email invalide")
    private String email;

    @Size(max = 100, message = "Le nom du responsable ne peut pas dépasser 100 caractères")
    private String nomResponsable;

    @Size(max = 20, message = "Le téléphone du responsable ne peut pas dépasser 20 caractères")
    @Pattern(regexp = "^[0-9+\\-\\s]*$", message = "Le téléphone du responsable contient des caractères invalides")
    private String telephoneResponsable;

    @Size(max = 100, message = "L'email du responsable ne peut pas dépasser 100 caractères")
    @Email(message = "Format d'email du responsable invalide")
    private String emailResponsable;

    @NotNull(message = "Le statut actif est obligatoire")
    private Boolean actif = true;
}