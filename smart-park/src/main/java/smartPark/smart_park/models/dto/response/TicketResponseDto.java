package smartPark.smart_park.models.dto.response;

import lombok.Getter;
import lombok.Setter;
import smartPark.smart_park.models.entity.enums.EtatTicket;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketResponseDto {
    private Long id;
    private String descriptionProbleme;
    private LocalDateTime dateCreation;
    private EtatTicket etat;
    private String etatLibelle;

    // Infos Immo
    private Long immobilisationId;
    private String immobilisationCode;
    private String immobilisationDesignation;

    // Infos Demandeur
    private Long demandeurId;
    private String demandeurNom;

    // Infos Validateur (optionnel)
    private Long validateurId;
    private String validateurNom;
    private LocalDateTime dateValidation;
    private String motifRejet;

    // Infos Intervention (optionnel)
    private Long interventionId;
}