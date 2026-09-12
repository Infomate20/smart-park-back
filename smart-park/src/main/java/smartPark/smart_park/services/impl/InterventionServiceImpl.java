package smartPark.smart_park.services.impl;

import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import smartPark.smart_park.models.dto.request.InterventionRequestDto;
import smartPark.smart_park.models.dto.request.InterventionUpdateDto;
import smartPark.smart_park.models.dto.response.InterventionResponseDto;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.mapper.InterventionMapper;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.Intervention;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.models.entity.enums.Role;
import smartPark.smart_park.models.entity.enums.EtatIntervention;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import smartPark.smart_park.repository.ImmobilisationRepository;
import smartPark.smart_park.repository.InterventionRepository;
import smartPark.smart_park.repository.UtilisateurRepository;
import smartPark.smart_park.services.InterventionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InterventionServiceImpl implements InterventionService {
    @Autowired
    private final InterventionRepository interventionRepository;
    @Autowired
    private final ImmobilisationRepository immobilisationRepository;
    @Autowired
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    private final InterventionMapper interventionMapper;

    @Override
    public InterventionResponseDto creerIntervention(InterventionRequestDto requestDto) {
        log.info("Création d'une nouvelle intervention pour l'immobilisation ID: {}", requestDto.getImmobilisationId());

        // Vérifier que l'immobilisation existe
        Immobilisation immobilisation = immobilisationRepository.findById(requestDto.getImmobilisationId())
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + requestDto.getImmobilisationId()));

        // Vérifier que le technicien existe et a le bon rôle
        Utilisateur technicien = utilisateurRepository.findById(requestDto.getTechnicienId())
                .orElseThrow(() -> new ResourceNotFoundException("Technicien non trouvé avec l'ID: " + requestDto.getTechnicienId()));

        if (!technicien.getRole().equals(Role.TECHNICIEN) && !technicien.getRole().equals(Role.ADMIN)) {
            throw new BusinessException("L'utilisateur doit avoir le rôle TECHNICIEN ou ADMIN pour effectuer une intervention");
        }

        Intervention intervention = interventionMapper.toEntity(requestDto);
        intervention.setImmobilisation(immobilisation);
        intervention.setTechnicien(technicien);

        if (requestDto.getEtatIntervention() != null) {
            intervention.setEtatIntervention(requestDto.getEtatIntervention());
        } else {
            intervention.setEtatIntervention(EtatIntervention.PLANIFIER);
        }
        intervention.setDateCloture(null);

        Intervention savedIntervention = interventionRepository.save(intervention);
        log.info("Intervention créée avec succès avec l'etat '{}'. ID: {}", savedIntervention.getEtatIntervention(), savedIntervention.getId());

        return interventionMapper.toResponseDto(savedIntervention);
    }

    @Override
    @Transactional(readOnly = true)
    public InterventionResponseDto obtenirInterventionParId(Long id) {
        log.info("Récupération de l'intervention ID: {}", id);

        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention non trouvée avec l'ID: " + id));

        return interventionMapper.toResponseDto(intervention);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterventionResponseDto> obtenirToutesLesInterventions(Pageable pageable) {
        log.info("Récupération de toutes les interventions avec pagination");

        Page<Intervention> interventions = interventionRepository.findAll(pageable);
        return interventions.map(interventionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponseDto> obtenirToutesLesInterventions() {
        log.info("Récupération de toutes les interventions");

        List<Intervention> interventions = interventionRepository.findAll();
        return interventionMapper.toResponseDtoList(interventions);
    }

    @Override
    public InterventionResponseDto mettreAJourIntervention(Long id, InterventionUpdateDto updateDto) {
        log.info("Mise à jour de l'intervention ID: {}", id);

        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention non trouvée avec l'ID: " + id));

        // Vérifier les nouvelles relations si elles sont modifiées
        if (updateDto.getImmobilisationId() != null && !updateDto.getImmobilisationId().equals(intervention.getImmobilisation().getId())) {
            Immobilisation immobilisation = immobilisationRepository.findById(updateDto.getImmobilisationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + updateDto.getImmobilisationId()));
            intervention.setImmobilisation(immobilisation);
        }

        if (updateDto.getTechnicienId() != null && !updateDto.getTechnicienId().equals(intervention.getTechnicien().getId())) {
            Utilisateur technicien = utilisateurRepository.findById(updateDto.getTechnicienId())
                    .orElseThrow(() -> new ResourceNotFoundException("Technicien non trouvé avec l'ID: " + updateDto.getTechnicienId()));

            if (!technicien.getRole().equals(Role.TECHNICIEN) && !technicien.getRole().equals(Role.ADMIN)) {
                throw new BusinessException("L'utilisateur doit avoir le rôle TECHNICIEN ou ADMIN");
            }
            intervention.setTechnicien(technicien);
        }

        interventionMapper.updateEntity(intervention, updateDto);
        Intervention updatedIntervention = interventionRepository.save(intervention);

        log.info("Intervention mise à jour avec succès. ID: {}", updatedIntervention.getId());
        return interventionMapper.toResponseDto(updatedIntervention);
    }

    @Override
    public void supprimerIntervention(Long id) {
        log.info("Suppression de l'intervention ID: {}", id);

        if (!interventionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Intervention non trouvée avec l'ID: " + id);
        }

        interventionRepository.deleteById(id);
        log.info("Intervention supprimée avec succès. ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponseDto> obtenirInterventionsParImmobilisation(Long immobilisationId) {
        log.info("Récupération des interventions pour l'immobilisation ID: {}", immobilisationId);

        // Vérifier que l'immobilisation existe
        if (!immobilisationRepository.existsById(immobilisationId)) {
            throw new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId);
        }

        List<Intervention> interventions = interventionRepository.findByImmobilisationId(immobilisationId);
        return interventionMapper.toResponseDtoList(interventions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterventionResponseDto> obtenirInterventionsParImmobilisation(Long immobilisationId, Pageable pageable) {
        log.info("Récupération des interventions pour l'immobilisation ID: {} avec pagination", immobilisationId);

        if (!immobilisationRepository.existsById(immobilisationId)) {
            throw new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId);
        }

        Page<Intervention> interventions = interventionRepository.findByImmobilisationId(immobilisationId, pageable);
        return interventions.map(interventionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponseDto> obtenirInterventionsParTechnicien(Long technicienId) {
        log.info("Récupération des interventions pour le technicien ID: {}", technicienId);

        if (!utilisateurRepository.existsById(technicienId)) {
            throw new ResourceNotFoundException("Technicien non trouvé avec l'ID: " + technicienId);
        }

        List<Intervention> interventions = interventionRepository.findByTechnicienId(technicienId);
        return interventionMapper.toResponseDtoList(interventions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterventionResponseDto> obtenirInterventionsParTechnicien(Long technicienId, Pageable pageable) {
        log.info("Récupération des interventions pour le technicien ID: {} avec pagination", technicienId);

        if (!utilisateurRepository.existsById(technicienId)) {
            throw new ResourceNotFoundException("Technicien non trouvé avec l'ID: " + technicienId);
        }

        Page<Intervention> interventions = interventionRepository.findByTechnicienId(technicienId, pageable);
        return interventions.map(interventionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponseDto> obtenirInterventionsParType(TypeIntervention typeIntervention) {
        log.info("Récupération des interventions par type: {}", typeIntervention);

        List<Intervention> interventions = interventionRepository.findByTypeIntervention(typeIntervention);
        return interventionMapper.toResponseDtoList(interventions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterventionResponseDto> obtenirInterventionsParType(TypeIntervention typeIntervention, Pageable pageable) {
        log.info("Récupération des interventions par type: {} avec pagination", typeIntervention);

        Page<Intervention> interventions = interventionRepository.findByTypeIntervention(typeIntervention, pageable);
        return interventions.map(interventionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponseDto> obtenirInterventionsParPeriode(LocalDateTime dateDebut, LocalDateTime dateFin) {
        log.info("Récupération des interventions entre {} et {}", dateDebut, dateFin);

        if (dateDebut.isAfter(dateFin)) {
            throw new ValidationException("La date de début doit être antérieure à la date de fin");
        }

        List<Intervention> interventions = interventionRepository.findByDateInterventionBetween(dateDebut, dateFin);
        return interventionMapper.toResponseDtoList(interventions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponseDto> obtenirInterventionsParAgence(Long agenceId) {
        log.info("Récupération des interventions pour l'agence ID: {}", agenceId);

        List<Intervention> interventions = interventionRepository.findByAgenceId(agenceId);
        return interventionMapper.toResponseDtoList(interventions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterventionResponseDto> obtenirInterventionsParAgence(Long agenceId, Pageable pageable) {
        log.info("Récupération des interventions pour l'agence ID: {} avec pagination", agenceId);

        Page<Intervention> interventions = interventionRepository.findByAgenceId(agenceId, pageable);
        return interventions.map(interventionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterventionResponseDto> rechercherAvecCriteres(
            Long immobilisationId,
            Long technicienId,
            TypeIntervention typeIntervention,
            LocalDateTime dateDebut,
            LocalDateTime dateFin,
            Pageable pageable) {

        log.info("Recherche d'interventions avec critères multiples");

        if (dateDebut != null && dateFin != null && dateDebut.isAfter(dateFin)) {
            throw new ValidationException("La date de début doit être antérieure à la date de fin");
        }

        Page<Intervention> interventions = interventionRepository.findWithCriteria(
                immobilisationId, technicienId, typeIntervention, dateDebut, dateFin, pageable
        );

        return interventions.map(interventionMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterventionResponseDto> obtenirDernieresInterventions(Long immobilisationId, int limite) {
        log.info("Récupération des {} dernières interventions pour l'immobilisation ID: {}", limite, immobilisationId);

        if (!immobilisationRepository.existsById(immobilisationId)) {
            throw new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId);
        }

        List<Intervention> interventions = interventionRepository.findLastInterventionsByImmobilisation(immobilisationId);

        // Limiter le nombre de résultats
        if (interventions.size() > limite) {
            interventions = interventions.subList(0, limite);
        }

        return interventionMapper.toResponseDtoList(interventions);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenirStatistiquesParType() {
        log.info("Récupération des statistiques par type d'intervention");

        return interventionRepository.countInterventionsByType();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean interventionExiste(Long id) {
        return interventionRepository.existsById(id);
    }


    @Override
    @Transactional
    public InterventionResponseDto commencerIntervention(Long id) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention non trouvée"));

        if (intervention.getEtatIntervention() != EtatIntervention.PLANIFIER) {
            throw new BusinessException("L'intervention ne peut pas être commencée car elle n'est pas planifiée.");
        }

        intervention.setEtatIntervention(EtatIntervention.EN_COURS);
        Intervention interventionMiseAJour = interventionRepository.save(intervention);
        return interventionMapper.toResponseDto(interventionMiseAJour);
    }
    @Override
    @Transactional
    public InterventionResponseDto terminerIntervention(Long id, LocalDateTime dateFin) {
        Intervention intervention = interventionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intervention non trouvée"));

        // Logique métier : on ne peut terminer qu'une intervention en cours
        if (intervention.getEtatIntervention() != EtatIntervention.EN_COURS) {
            throw new BusinessException("L'intervention ne peut pas être terminée car elle n'est pas en cours.");
        }

        intervention.setEtatIntervention(EtatIntervention.TERMINER);
        intervention.setDateCloture(dateFin != null ? dateFin : LocalDateTime.now()); // Date de fin = maintenant si non fournie

        Intervention interventionMiseAJour = interventionRepository.save(intervention);
        return interventionMapper.toResponseDto(interventionMiseAJour);
    }


}