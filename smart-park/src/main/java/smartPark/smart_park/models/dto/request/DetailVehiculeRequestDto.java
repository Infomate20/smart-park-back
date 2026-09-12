package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.TypeCarburant;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVehiculeRequestDto {

    @NotBlank(message = "L'immatriculation est obligatoire")
    @Size(max = 20, message = "L'immatriculation ne peut pas dépasser 20 caractères")
    private String immatriculation;

    @Min(value = 0, message = "Le kilométrage doit être positif ou nul")
    private Integer kilometrage;

    private TypeCarburant typeCarburant;

    @Min(value = 1, message = "La puissance fiscale doit être d'au moins 1 CV")
    @Max(value = 100, message = "La puissance fiscale ne peut pas dépasser 100 CV")
    private Integer puissanceFiscale;

    @Min(value = 1, message = "Le nombre de places doit être d'au moins 1")
    @Max(value = 100, message = "Le nombre de places ne peut pas dépasser 100")
    private Integer nombrePlaces;

    private LocalDate dateDernierControleTechnique;
    private LocalDate dateProchainControleTechnique;

    @Size(max = 100, message = "La compagnie d'assurance ne peut pas dépasser 100 caractères")
    private String compagnieAssurance;

    @Size(max = 50, message = "Le numéro de police ne peut pas dépasser 50 caractères")
    private String numeroPoliceAssurance;

    private LocalDate dateExpirationAssurance;

    @Size(max = 1000, message = "Les observations ne peuvent pas dépasser 1000 caractères")
    private String observations;
}
