package smartPark.smart_park.services;

import smartPark.smart_park.models.dto.request.InterventionRequestDto;
import smartPark.smart_park.models.dto.request.InterventionUpdateDto;
import smartPark.smart_park.models.dto.response.InterventionResponseDto;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface InterventionService {

    /**
     * Créer une nouvelle intervention
     */
    InterventionResponseDto creerIntervention(InterventionRequestDto requestDto);

    InterventionResponseDto terminerIntervention(Long id, LocalDateTime dateFin);

    InterventionResponseDto commencerIntervention(Long id);

    /**
     * Récupérer une intervention par son ID
     */
    InterventionResponseDto obtenirInterventionParId(Long id);

    /**
     * Récupérer toutes les interventions avec pagination
     */
    Page<InterventionResponseDto> obtenirToutesLesInterventions(Pageable pageable);

    /**
     * Récupérer toutes les interventions sans pagination
     */
    List<InterventionResponseDto> obtenirToutesLesInterventions();

    /**
     * Mettre à jour une intervention
     */
    InterventionResponseDto mettreAJourIntervention(Long id, InterventionUpdateDto updateDto);

    /**
     * Supprimer une intervention
     */
    void supprimerIntervention(Long id);

    /**
     * Récupérer les interventions par immobilisation
     */
    List<InterventionResponseDto> obtenirInterventionsParImmobilisation(Long immobilisationId);

    /**
     * Récupérer les interventions par immobilisation avec pagination
     */
    Page<InterventionResponseDto> obtenirInterventionsParImmobilisation(Long immobilisationId, Pageable pageable);

    /**
     * Récupérer les interventions par technicien
     */
    List<InterventionResponseDto> obtenirInterventionsParTechnicien(Long technicienId);

    /**
     * Récupérer les interventions par technicien avec pagination
     */
    Page<InterventionResponseDto> obtenirInterventionsParTechnicien(Long technicienId, Pageable pageable);

    /**
     * Récupérer les interventions par type
     */
    List<InterventionResponseDto> obtenirInterventionsParType(TypeIntervention typeIntervention);

    /**
     * Récupérer les interventions par type avec pagination
     */
    Page<InterventionResponseDto> obtenirInterventionsParType(TypeIntervention typeIntervention, Pageable pageable);

    /**
     * Récupérer les interventions par période
     */
    List<InterventionResponseDto> obtenirInterventionsParPeriode(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Récupérer les interventions par agence
     */
    List<InterventionResponseDto> obtenirInterventionsParAgence(Long agenceId);

    /**
     * Récupérer les interventions par agence avec pagination
     */
    Page<InterventionResponseDto> obtenirInterventionsParAgence(Long agenceId, Pageable pageable);

    /**
     * Recherche avec critères multiples
     */
    Page<InterventionResponseDto> rechercherAvecCriteres(
            Long immobilisationId,
            Long technicienId,
            TypeIntervention typeIntervention,
            LocalDateTime dateDebut,
            LocalDateTime dateFin,
            Pageable pageable
    );

    /**
     * Récupérer les dernières interventions d'une immobilisation
     */
    List<InterventionResponseDto> obtenirDernieresInterventions(Long immobilisationId, int limite);

    /**
     * Obtenir les statistiques par type d'intervention
     */
    List<Object[]> obtenirStatistiquesParType();

    /**
     * Vérifier si une intervention existe
     */
    boolean interventionExiste(Long id);


}