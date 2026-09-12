package smartPark.smart_park.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.InterventionRequestDto;
import smartPark.smart_park.models.dto.request.InterventionUpdateDto;
import smartPark.smart_park.models.dto.response.InterventionResponseDto;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import smartPark.smart_park.services.InterventionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/interventions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Interventions", description = "API de gestion des interventions")
public class InterventionController {
     @Autowired
    private final InterventionService interventionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Créer une nouvelle intervention", description = "Permet de créer une nouvelle intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Intervention créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "Immobilisation ou technicien non trouvé")
    })
    public ResponseEntity<InterventionResponseDto> creerIntervention(
            @Valid @RequestBody InterventionRequestDto requestDto) {

        log.info("Demande de création d'intervention pour l'immobilisation ID: {}", requestDto.getImmobilisationId());
        InterventionResponseDto response = interventionService.creerIntervention(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN') or hasRole('AGENT')")
    @Operation(summary = "Récupérer une intervention par ID", description = "Permet de récupérer les détails d'une intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervention récupérée avec succès"),
            @ApiResponse(responseCode = "404", description = "Intervention non trouvée")
    })
    public ResponseEntity<InterventionResponseDto> obtenirInterventionParId(
            @Parameter(description = "ID de l'intervention") @PathVariable Long id) {

        log.info("Demande de récupération de l'intervention ID: {}", id);
        InterventionResponseDto response = interventionService.obtenirInterventionParId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN') or hasRole('AGENT')")
    @Operation(summary = "Récupérer toutes les interventions", description = "Permet de récupérer la liste des interventions avec pagination")
    @ApiResponse(responseCode = "200", description = "Liste des interventions récupérée avec succès")
    public ResponseEntity<Page<InterventionResponseDto>> obtenirToutesLesInterventions(
            @Parameter(description = "Numéro de page (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "dateIntervention") String sortBy,
            @Parameter(description = "Direction du tri") @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande de récupération de toutes les interventions - Page: {}, Size: {}, Sort: {} {}", page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InterventionResponseDto> response = interventionService.obtenirToutesLesInterventions(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Récupérer toutes les interventions sans pagination", description = "Permet de récupérer toutes les interventions")
    @ApiResponse(responseCode = "200", description = "Liste complète des interventions récupérée avec succès")
    public ResponseEntity<List<InterventionResponseDto>> obtenirToutesLesInterventionsSansPagination() {
        log.info("Demande de récupération de toutes les interventions sans pagination");
        List<InterventionResponseDto> response = interventionService.obtenirToutesLesInterventions();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Mettre à jour une intervention", description = "Permet de modifier les informations d'une intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intervention mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "Intervention non trouvée")
    })
    public ResponseEntity<InterventionResponseDto> mettreAJourIntervention(
            @Parameter(description = "ID de l'intervention") @PathVariable Long id,
            @Valid @RequestBody InterventionUpdateDto updateDto) {

        log.info("Demande de mise à jour de l'intervention ID: {}", id);
        InterventionResponseDto response = interventionService.mettreAJourIntervention(id, updateDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer une intervention", description = "Permet de supprimer une intervention")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Intervention supprimée avec succès"),
            @ApiResponse(responseCode = "403", description = "Accès refusé"),
            @ApiResponse(responseCode = "404", description = "Intervention non trouvée")
    })
    public ResponseEntity<Void> supprimerIntervention(
            @Parameter(description = "ID de l'intervention") @PathVariable Long id) {

        log.info("Demande de suppression de l'intervention ID: {}", id);
        interventionService.supprimerIntervention(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/immobilisation/{immobilisationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN') or hasRole('AGENT')")
    @Operation(summary = "Récupérer les interventions par immobilisation", description = "Permet de récupérer toutes les interventions d'une immobilisation")
    @ApiResponse(responseCode = "200", description = "Liste des interventions récupérée avec succès")
    public ResponseEntity<Page<InterventionResponseDto>> obtenirInterventionsParImmobilisation(
            @Parameter(description = "ID de l'immobilisation") @PathVariable Long immobilisationId,
            @Parameter(description = "Numéro de page (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "dateIntervention") String sortBy,
            @Parameter(description = "Direction du tri") @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des interventions pour l'immobilisation ID: {}", immobilisationId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InterventionResponseDto> response = interventionService.obtenirInterventionsParImmobilisation(immobilisationId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/technicien/{technicienId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Récupérer les interventions par technicien", description = "Permet de récupérer toutes les interventions d'un technicien")
    @ApiResponse(responseCode = "200", description = "Liste des interventions récupérée avec succès")
    public ResponseEntity<Page<InterventionResponseDto>> obtenirInterventionsParTechnicien(
            @Parameter(description = "ID du technicien") @PathVariable Long technicienId,
            @Parameter(description = "Numéro de page (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "dateIntervention") String sortBy,
            @Parameter(description = "Direction du tri") @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des interventions pour le technicien ID: {}", technicienId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InterventionResponseDto> response = interventionService.obtenirInterventionsParTechnicien(technicienId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{typeIntervention}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Récupérer les interventions par type", description = "Permet de récupérer toutes les interventions d'un type donné")
    @ApiResponse(responseCode = "200", description = "Liste des interventions récupérée avec succès")
    public ResponseEntity<Page<InterventionResponseDto>> obtenirInterventionsParType(
            @Parameter(description = "Type d'intervention") @PathVariable TypeIntervention typeIntervention,
            @Parameter(description = "Numéro de page (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "dateIntervention") String sortBy,
            @Parameter(description = "Direction du tri") @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des interventions par type: {}", typeIntervention);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InterventionResponseDto> response = interventionService.obtenirInterventionsParType(typeIntervention, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periode")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Récupérer les interventions par période", description = "Permet de récupérer les interventions dans une période donnée")
    @ApiResponse(responseCode = "200", description = "Liste des interventions récupérée avec succès")
    public ResponseEntity<List<InterventionResponseDto>> obtenirInterventionsParPeriode(
            @Parameter(description = "Date de début (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @Parameter(description = "Date de fin (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {

        log.info("Demande des interventions entre {} et {}", dateDebut, dateFin);
        List<InterventionResponseDto> response = interventionService.obtenirInterventionsParPeriode(dateDebut, dateFin);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/agence/{agenceId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN') or hasRole('AGENT')")
    @Operation(summary = "Récupérer les interventions par agence", description = "Permet de récupérer toutes les interventions d'une agence")
    @ApiResponse(responseCode = "200", description = "Liste des interventions récupérée avec succès")
    public ResponseEntity<Page<InterventionResponseDto>> obtenirInterventionsParAgence(
            @Parameter(description = "ID de l'agence") @PathVariable Long agenceId,
            @Parameter(description = "Numéro de page (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "dateIntervention") String sortBy,
            @Parameter(description = "Direction du tri") @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des interventions pour l'agence ID: {}", agenceId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InterventionResponseDto> response = interventionService.obtenirInterventionsParAgence(agenceId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recherche")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Recherche multicritères", description = "Permet de rechercher des interventions avec plusieurs critères")
    @ApiResponse(responseCode = "200", description = "Résultats de recherche récupérés avec succès")
    public ResponseEntity<Page<InterventionResponseDto>> rechercherAvecCriteres(
            @Parameter(description = "ID de l'immobilisation") @RequestParam(required = false) Long immobilisationId,
            @Parameter(description = "ID du technicien") @RequestParam(required = false) Long technicienId,
            @Parameter(description = "Type d'intervention") @RequestParam(required = false) TypeIntervention typeIntervention,
            @Parameter(description = "Date de début") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @Parameter(description = "Date de fin") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin,
            @Parameter(description = "Numéro de page (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "dateIntervention") String sortBy,
            @Parameter(description = "Direction du tri") @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Recherche d'interventions avec critères multiples");

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<InterventionResponseDto> response = interventionService.rechercherAvecCriteres(
                immobilisationId, technicienId, typeIntervention, dateDebut, dateFin, pageable
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/immobilisation/{immobilisationId}/dernieres")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN') or hasRole('AGENT')")
    @Operation(summary = "Récupérer les dernières interventions", description = "Permet de récupérer les X dernières interventions d'une immobilisation")
    @ApiResponse(responseCode = "200", description = "Dernières interventions récupérées avec succès")
    public ResponseEntity<List<InterventionResponseDto>> obtenirDernieresInterventions(
            @Parameter(description = "ID de l'immobilisation") @PathVariable Long immobilisationId,
            @Parameter(description = "Nombre d'interventions à récupérer") @RequestParam(defaultValue = "5") int limite) {

        log.info("Demande des {} dernières interventions pour l'immobilisation ID: {}", limite, immobilisationId);
        List<InterventionResponseDto> response = interventionService.obtenirDernieresInterventions(immobilisationId, limite);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistiques/types")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Statistiques par type d'intervention", description = "Permet d'obtenir les statistiques par type d'intervention")
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    public ResponseEntity<List<Object[]>> obtenirStatistiquesParType() {
        log.info("Demande des statistiques par type d'intervention");
        List<Object[]> response = interventionService.obtenirStatistiquesParType();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/existe")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN') or hasRole('AGENT')")
    @Operation(summary = "Vérifier l'existence d'une intervention", description = "Permet de vérifier si une intervention existe")
    @ApiResponse(responseCode = "200", description = "Vérification effectuée avec succès")
    public ResponseEntity<Boolean> interventionExiste(
            @Parameter(description = "ID de l'intervention") @PathVariable Long id) {

        log.info("Vérification de l'existence de l'intervention ID: {}", id);
        boolean existe = interventionService.interventionExiste(id);
        return ResponseEntity.ok(existe);
    }

    @PutMapping("/{id}/commencer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Marquer une intervention comme 'En cours'")
    public ResponseEntity<InterventionResponseDto> commencerIntervention(@PathVariable Long id) {
        log.info("Demande pour commencer l'intervention ID: {}", id);
        InterventionResponseDto response = interventionService.commencerIntervention(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/terminer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TECHNICIEN')")
    @Operation(summary = "Marquer une intervention comme 'Terminée'")
    public ResponseEntity<InterventionResponseDto> terminerIntervention(
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {
        log.info("Demande pour terminer l'intervention ID: {}", id);
        InterventionResponseDto response = interventionService.terminerIntervention(id, dateFin);
        return ResponseEntity.ok(response);
    }
}
