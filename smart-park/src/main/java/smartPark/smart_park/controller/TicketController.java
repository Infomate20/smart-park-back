package smartPark.smart_park.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.TicketRequestDto;
import smartPark.smart_park.models.dto.request.TicketValidationRequestDto;
import smartPark.smart_park.models.dto.response.TicketResponseDto;
import smartPark.smart_park.services.TicketService;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Créer un nouveau ticket de panne")
    public ResponseEntity<TicketResponseDto> creerTicket(@Valid @RequestBody TicketRequestDto requestDto, Authentication authentication) {
        log.info("Requête de création de ticket reçue");
        TicketResponseDto nouveauTicket = ticketService.creerTicket(requestDto, authentication.getName());
        return new ResponseEntity<>(nouveauTicket, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lister tous les tickets avec pagination et tri")
    public ResponseEntity<Page<TicketResponseDto>> listerTickets(@PageableDefault(size = 10, sort = "dateCreation") Pageable pageable) {
        Page<TicketResponseDto> tickets = ticketService.listerTousLesTickets(pageable);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtenir les détails d'un ticket spécifique")
    public ResponseEntity<TicketResponseDto> obtenirTicketParId(@PathVariable Long id) {
        TicketResponseDto ticket = ticketService.obtenirTicketParId(id);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/{id}/valider")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Valider ou rejeter un ticket (action administrateur)")
    public ResponseEntity<TicketResponseDto> validerOuRejeterTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketValidationRequestDto requestDto,
            Authentication authentication) {
        log.info("Requête de validation pour le ticket ID: {}", id);
        TicketResponseDto ticketMisAJour = ticketService.validerOuRejeterTicket(id, requestDto, authentication.getName());
        return ResponseEntity.ok(ticketMisAJour);
    }

    // On pourrait ajouter un endpoint DELETE si nécessaire
    // @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    // public ResponseEntity<Void> supprimerTicket(@PathVariable Long id) {
    //     ticketService.supprimerTicket(id);
    //     return ResponseEntity.noContent().build();
    // }
}