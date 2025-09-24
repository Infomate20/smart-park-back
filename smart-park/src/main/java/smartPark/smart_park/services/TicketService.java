package smartPark.smart_park.services;

import smartPark.smart_park.models.dto.request.TicketRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.request.TicketRequestDto;
import smartPark.smart_park.models.dto.response.TicketResponseDto;
import smartPark.smart_park.models.dto.request.TicketValidationRequestDto;

public interface TicketService {

    /**
     * Crée un nouveau ticket à partir d'une demande.
     * @param requestDto Les données pour la création du ticket.
     * @param demandeurEmail L'email de l'utilisateur qui crée le ticket.
     * @return Le DTO du ticket nouvellement créé.
     */
    TicketResponseDto creerTicket(TicketRequestDto requestDto, String demandeurEmail);

    /**
     * Récupère une liste paginée de tous les tickets.
     * @param pageable Les informations de pagination et de tri.
     * @return Une page de DTOs de tickets.
     */
    Page<TicketResponseDto> listerTousLesTickets(Pageable pageable);

    /**
     * Récupère les détails d'un ticket par son ID.
     * @param id L'ID du ticket à récupérer.
     * @return Le DTO du ticket correspondant.
     * @throws smartPark.smart_park.exceptions.ResourceNotFoundException si le ticket n'est pas trouvé.
     */
    TicketResponseDto obtenirTicketParId(Long id);

    /**
     * Traite un ticket en le validant ou en le rejetant.
     * @param ticketId L'ID du ticket à traiter.
     * @param requestDto Les données de la décision (valider/rejeter + motif).
     * @param validateurEmail L'email de l'administrateur qui traite le ticket.
     * @return Le DTO du ticket mis à jour.
     */
    TicketResponseDto validerOuRejeterTicket(Long ticketId, TicketValidationRequestDto requestDto, String validateurEmail);

    // Si vous souhaitez ajouter la suppression plus tard :
    // void supprimerTicket(Long id);
}