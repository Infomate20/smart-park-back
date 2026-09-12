package smartPark.smart_park.models.dto.response;


import smartPark.smart_park.models.entity.enums.EtatIntervention;
import smartPark.smart_park.models.entity.enums.Orientation;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterventionResponseDto {

    private Long id;
    private String description;
    private LocalDateTime dateIntervention;
    private BigDecimal coutIntervention;
    private String observations;
    private TypeIntervention typeIntervention;
    private Orientation orientation;
    private EtatIntervention etatIntervention;
    private String etatLibelle;
    private LocalDateTime dateCloture;

    // Informations de l'immobilisation
    private Long immobilisationId;
    private String immobilisationCode;
    private String immobilisationDesignation;

    // Informations du technicien
    private Long technicienId;
    private String technicienNom;
    private String technicienEmail;

    // Informations de l'agence
    private Long agenceId;
    private String agenceNom;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}