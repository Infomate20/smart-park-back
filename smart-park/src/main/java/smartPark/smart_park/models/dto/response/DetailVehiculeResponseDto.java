package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.TypeCarburant;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVehiculeResponseDto {

    private Long id;

    // Rappel du bien porteur
    private Long immobilisationId;
    private String codeImmobilisation;
    private String designation;

    private String immatriculation;
    private Integer kilometrage;
    private TypeCarburant typeCarburant;
    private String typeCarburantLibelle;
    private Integer puissanceFiscale;
    private Integer nombrePlaces;

    private LocalDate dateDernierControleTechnique;
    private LocalDate dateProchainControleTechnique;
    /** Faux si l'échéance du contrôle technique est dépassée ; null si non renseignée. */
    private Boolean controleTechniqueAJour;

    private String compagnieAssurance;
    private String numeroPoliceAssurance;
    private LocalDate dateExpirationAssurance;
    /** Faux si l'assurance est expirée ; null si la date n'est pas renseignée. */
    private Boolean assuranceAJour;

    private String observations;

    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}
