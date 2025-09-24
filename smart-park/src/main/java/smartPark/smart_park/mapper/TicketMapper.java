package smartPark.smart_park.mapper;

import org.springframework.stereotype.Component;
import smartPark.smart_park.models.dto.response.TicketResponseDto;
import smartPark.smart_park.models.entity.Ticket;

@Component // On le déclare comme un composant Spring pour pouvoir l'injecter
public class TicketMapper {

    /**
     * Convertit une entité Ticket en son DTO de réponse.
     * @param ticket L'entité à convertir.
     * @return Le DTO correspondant, ou null si l'entité est nulle.
     */
    public TicketResponseDto toResponseDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        TicketResponseDto dto = new TicketResponseDto();

        // Mappage des champs directs
        dto.setId(ticket.getId());
        dto.setDescriptionProbleme(ticket.getDescriptionProbleme());
        dto.setDateCreation(ticket.getDateCreation());
        dto.setEtat(ticket.getEtat());
        dto.setEtatLibelle(ticket.getEtat().getLibelle()); // Utilise le getter de l'enum
        dto.setDateValidation(ticket.getDateValidation());
        dto.setMotifRejet(ticket.getMotifRejet());

        // Mappage des informations de l'immobilisation
        if (ticket.getImmobilisation() != null) {
            dto.setImmobilisationId(ticket.getImmobilisation().getId());
            dto.setImmobilisationCode(ticket.getImmobilisation().getCodeImmobilisation());
            dto.setImmobilisationDesignation(ticket.getImmobilisation().getDesignation());
        }

        // Mappage des informations du demandeur
        if (ticket.getDemandeur() != null) {
            dto.setDemandeurId(ticket.getDemandeur().getId());
            dto.setDemandeurNom(ticket.getDemandeur().getNomUtilisateur());
        }

        // Mappage des informations du validateur (peut être null)
        if (ticket.getValidateur() != null) {
            dto.setValidateurId(ticket.getValidateur().getId());
            dto.setValidateurNom(ticket.getValidateur().getNomUtilisateur());
        }

        // Mappage de l'intervention liée (peut être null)
        if (ticket.getInterventionLiee() != null) {
            dto.setInterventionId(ticket.getInterventionLiee().getId());
        }

        return dto;
    }

    // Si vous avez besoin de convertir un DTO en entité (pour la création par exemple),
    // vous pouvez ajouter la méthode ici. Cependant, la logique actuelle du service
    // crée une nouvelle entité manuellement, ce qui est aussi une bonne approche.
    //
    // public Ticket toEntity(TicketRequestDto dto) { ... }
}