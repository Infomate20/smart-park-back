package smartPark.smart_park.services.impl;

import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.mapper.TicketMapper;
import smartPark.smart_park.models.dto.request.TicketRequestDto;
import smartPark.smart_park.models.dto.request.TicketValidationRequestDto;
import smartPark.smart_park.models.dto.response.TicketResponseDto;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.Intervention;
import smartPark.smart_park.models.entity.Ticket;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;
import smartPark.smart_park.models.entity.enums.EtatTicket;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import smartPark.smart_park.models.entity.enums.EtatIntervention;
import smartPark.smart_park.repository.ImmobilisationRepository;
import smartPark.smart_park.repository.InterventionRepository;
import smartPark.smart_park.repository.TicketRepository;
import smartPark.smart_park.repository.UtilisateurRepository;
import smartPark.smart_park.services.TicketService;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService { // "implements TicketService"

    private final TicketRepository ticketRepository;
    private final ImmobilisationRepository immobilisationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final TicketMapper ticketMapper;
    private final InterventionRepository interventionRepository;

    @Override
    public TicketResponseDto creerTicket(TicketRequestDto requestDto, String demandeurEmail) {
        Utilisateur demandeur = utilisateurRepository.findByNomUtilisateur(demandeurEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + demandeurEmail));

        Immobilisation immobilisation = immobilisationRepository.findById(requestDto.getImmobilisationId())
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + requestDto.getImmobilisationId()));

        Ticket ticket = new Ticket();
        ticket.setDemandeur(demandeur);
        ticket.setImmobilisation(immobilisation);
        ticket.setDescriptionProbleme(requestDto.getDescriptionProbleme());
        // L'état et la date sont gérés par @PrePersist dans l'entité

        Ticket savedTicket = ticketRepository.save(ticket);
        log.info("Nouveau ticket ID {} créé par {}", savedTicket.getId(), demandeurEmail);

        return ticketMapper.toResponseDto(savedTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketResponseDto> listerTousLesTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable).map(ticketMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponseDto obtenirTicketParId(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket non trouvé avec l'ID: " + id));
        return ticketMapper.toResponseDto(ticket);
    }

    @Override
    public TicketResponseDto validerOuRejeterTicket(Long ticketId, TicketValidationRequestDto requestDto, String validateurEmail) {
        Utilisateur validateur = utilisateurRepository.findByNomUtilisateur(validateurEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Validateur non trouvé: " + validateurEmail));

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket non trouvé avec l'ID: " + ticketId));

        if (ticket.getEtat() != EtatTicket.NOUVEAU) {
            throw new BusinessException("Ce ticket a déjà été traité et ne peut plus être modifié.");
        }

        ticket.setValidateur(validateur);
        ticket.setDateValidation(LocalDateTime.now());

        if (Boolean.TRUE.equals(requestDto.getValider())) { // Utilisation de Boolean.TRUE.equals pour éviter les NullPointerException
            // Cas où l'admin VALIDE le ticket

            if (requestDto.isCreerIntervention()) {
                // L'admin a coché la case "Créer une intervention"
                log.info("Ticket ID {} validé par {}. Création d'une intervention associée.", ticketId, validateurEmail);

                Intervention intervention = new Intervention();
                intervention.setImmobilisation(ticket.getImmobilisation());
                intervention.setTechnicien(ticket.getDemandeur()); // Technicien non assigné
                intervention.setDateIntervention(LocalDateTime.now());
                intervention.setDescription("Suite au ticket #" + ticket.getId() + ": " + ticket.getDescriptionProbleme());
                intervention.setTypeIntervention(TypeIntervention.MAINTENANCE_CORRECTIVE);
                intervention.setEtatIntervention(EtatIntervention.PLANIFIER);

                Intervention savedIntervention = interventionRepository.save(intervention);

                ticket.setInterventionLiee(savedIntervention);
                ticket.setEtat(EtatTicket.EN_COURS_DE_TRAITEMENT); // Le ticket est maintenant "pris en charge"

            } else {
                // L'admin a validé le ticket mais n'a PAS coché la case
                log.info("Ticket ID {} validé par {}. Aucune intervention créée pour le moment.", ticketId, validateurEmail);
                ticket.setEtat(EtatTicket.VALIDE); // Le ticket est simplement validé
            }

            // Mettre l'immobilisation en panne dans les deux cas de validation
            Immobilisation immo = ticket.getImmobilisation();
            immo.setEtat(EtatImmobilisation.EN_PANNE);
            immobilisationRepository.save(immo);

        } else {
            // Cas où l'admin REJETTE le ticket
            if (requestDto.getMotifRejet() == null || requestDto.getMotifRejet().isBlank()) {
                throw new ValidationException("Le motif de rejet est obligatoire lorsque le ticket est rejeté.");
            }
            ticket.setEtat(EtatTicket.REJETE);
            ticket.setMotifRejet(requestDto.getMotifRejet());
            log.info("Ticket ID {} rejeté par {}.", ticketId, validateurEmail);
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return ticketMapper.toResponseDto(savedTicket);
    }
}