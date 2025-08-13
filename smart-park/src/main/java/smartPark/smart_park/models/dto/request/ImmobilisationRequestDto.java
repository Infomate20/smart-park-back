package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImmobilisationRequestDto {

    @NotBlank(message = "Le numéro de série est obligatoire")
    @Size(min = 1, max = 100, message = "Le numéro de série doit contenir entre 1 et 100 caractères")
    private String numeroSerie;

    @NotBlank(message = "La désignation est obligatoire")
    @Size(min = 2, max = 200, message = "La désignation doit contenir entre 2 et 200 caractères")
    private String designation;

    @Size(max = 100, message = "La marque ne peut pas dépasser 100 caractères")
    private String marque;

    @Size(max = 100, message = "Le modèle ne peut pas dépasser 100 caractères")
    private String modele;

    @Past(message = "La date d'acquisition doit être dans le passé")
    private LocalDate dateAcquisition;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix d'acquisition doit être positif")
    @Digits(integer = 13, fraction = 2, message = "Le prix d'acquisition ne peut avoir plus de 13 chiffres avant la virgule et 2 après")
    private BigDecimal prixAcquisition;

    @DecimalMin(value = "0.0", message = "La valeur actuelle doit être positive ou nulle")
    @Digits(integer = 13, fraction = 2, message = "La valeur actuelle ne peut avoir plus de 13 chiffres avant la virgule et 2 après")
    private BigDecimal valeurActuelle;

    @NotNull(message = "L'état de l'immobilisation est obligatoire")
    private EtatImmobilisation etat;

    private LocalDate dateMiseEnService;

    @Min(value = 0, message = "La durée de garantie doit être positive ou nulle")
    @Max(value = 240, message = "La durée de garantie ne peut pas dépasser 240 mois (20 ans)")
    private Integer dureeGarantieMois;

    @Size(max = 1000, message = "Les observations ne peuvent pas dépasser 1000 caractères")
    private String observations;

    @NotNull(message = "La catégorie est obligatoire")
    private Long categorieId;

    private Long agenceId; // Optionnel

    @NotNull(message = "Le statut actif est obligatoire")
    private Boolean actif = true;
}
