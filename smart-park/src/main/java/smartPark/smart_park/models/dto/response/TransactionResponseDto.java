package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.EtatTransaction;
import smartPark.smart_park.models.entity.enums.TypeTransaction;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private Long id;
    private TypeTransaction typeTransaction;
    private EtatTransaction etatTransaction;
    private LocalDateTime dateDemande;
    private LocalDateTime dateValidation;
    private String motif;
    private String observations;
    private String motifRejet;

    // Informations de l'immobilisation
    private Long immobilisationId;
    private String immobilisationCode;
    private String immobilisationDesignation;

    // Informations de l'agence source
    private Long agenceSourceId;
    private String agenceSourceNom;
    private String agenceSourceCode;

    // Informations de l'agence destination
    private Long agenceDestinationId;
    private String agenceDestinationNom;
    private String agenceDestinationCode;

    // Informations du demandeur
    private Long demandeurId;
    private String demandeurNom;
    private String demandeurPrenom;
    private String demandeurEmail;

    // Informations du validateur
    private Long validateurId;
    private String validateurNom;
    private String validateurPrenom;
    private String validateurEmail;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}