package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorieRequestDto {

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @NotBlank(message = "Le code de la catégorie est obligatoire")
    @Pattern(regexp = "^[A-Za-z0-9]{2,10}$",
            message = "Le code doit contenir entre 2 et 10 caractères alphanumériques, sans espace ni accent")
    private String code;

    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    private String description;

    @Min(value = 1, message = "La durée d'amortissement doit être d'au moins 1 mois")
    @Max(value = 600, message = "La durée d'amortissement ne peut pas dépasser 600 mois")
    private Integer dureeAmortissementMois;

    private MethodeAmortissement methodeAmortissement = MethodeAmortissement.LINEAIRE;

    @NotNull(message = "Le statut actif est obligatoire")
    private Boolean actif = true;
}