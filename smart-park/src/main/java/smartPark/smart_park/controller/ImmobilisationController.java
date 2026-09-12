package smartPark.smart_park.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.response.ImmobilisationResponseDto;
import smartPark.smart_park.models.dto.response.InterventionResponseDto;
import smartPark.smart_park.models.dto.response.TransactionResponseDto;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;
import smartPark.smart_park.services.EtiquetteService;
import smartPark.smart_park.services.ImmobilisationService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/immobilisations")
@RequiredArgsConstructor
public class ImmobilisationController {

    private final EtiquetteService etiquetteService;
    private final ImmobilisationService immobilisationService;

    // ===== ENDPOINTS CRUD DE BASE =====

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    public ResponseEntity<ImmobilisationResponseDto> creerImmobilisation(@Valid @RequestBody ImmobilisationRequestDto requestDto) {
        ImmobilisationResponseDto immobilisation = immobilisationService.creerImmobilisation(requestDto);
        return new ResponseEntity<>(immobilisation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ImmobilisationResponseDto> obtenirImmobilisationParId(@PathVariable Long id) {
        ImmobilisationResponseDto immobilisation = immobilisationService.obtenirImmobilisationParId(id);
        return ResponseEntity.ok(immobilisation);
    }

    @GetMapping("/code/{code}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ImmobilisationResponseDto> obtenirImmobilisationParCode(@PathVariable String code) {
        ImmobilisationResponseDto immobilisation = immobilisationService.obtenirImmobilisationParCode(code);
        return ResponseEntity.ok(immobilisation);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ImmobilisationResponseDto>> obtenirToutesLesImmobilisations() {
        List<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirToutesLesImmobilisations();
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/actives")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ImmobilisationResponseDto>> obtenirImmobilisationsActives() {
        List<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsActives();
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/paginated")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationAvecPagination(
            @RequestParam(required = false) String searchTerm, // <-- PARAMÈTRE AJOUTÉ
            @RequestParam(required = false) Boolean actif,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationAvecPagination( searchTerm, actif,pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    public ResponseEntity<ImmobilisationResponseDto> modifierImmobilisation(
            @PathVariable Long id,
            @Valid @RequestBody ImmobilisationRequestDto requestDto) {
        ImmobilisationResponseDto immobilisation = immobilisationService.modifierImmobilisation(id, requestDto);
        return ResponseEntity.ok(immobilisation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerImmobilisation(@PathVariable Long id) {
        immobilisationService.supprimerImmobilisation(id);
        return ResponseEntity.noContent().build();
    }

    // ===== ENDPOINTS DE RECHERCHE =====

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ImmobilisationResponseDto>> rechercherImmobilisations(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.rechercherImmobilisations(searchTerm, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/categorie/{categorieId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParCategorie(
            @PathVariable Long categorieId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParCategorie(categorieId, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/agence/{agenceId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParAgence(
            @PathVariable Long agenceId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParAgence(agenceId, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/sans-agence")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsSansAgence(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsSansAgence(pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/etat/{etat}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParEtat(
            @PathVariable EtatImmobilisation etat,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParEtat(etat, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/periode-acquisition")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ImmobilisationResponseDto>> obtenirImmobilisationsParPeriodeAcquisition(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        List<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParPeriodeAcquisition(dateDebut, dateFin);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/plage-prix")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParPlagePrix(
            @RequestParam BigDecimal prixMin,
            @RequestParam BigDecimal prixMax,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParPlageGPrix(prixMin, prixMax, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    // ===== ENDPOINTS DE GESTION D'ÉTAT =====

    @PutMapping("/{id}/desactiver")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    public ResponseEntity<Void> desactiverImmobilisation(@PathVariable Long id) {
        immobilisationService.desactiverImmobilisation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activer")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    public ResponseEntity<Void> activerImmobilisation(@PathVariable Long id) {
        immobilisationService.activerImmobilisation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/changer-etat")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    public ResponseEntity<Void> changerEtatImmobilisation(
            @PathVariable Long id,
            @RequestParam EtatImmobilisation nouvelEtat) {
        immobilisationService.changerEtatImmobilisation(id, nouvelEtat);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS DE GESTION DES AGENCES =====

    // Réservé à l'ADMIN : ces deux endpoints déplacent un bien sans passer par le
    // workflow de validation des Transactions.
    @PutMapping("/{id}/transferer-agence")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> transfererVersAgence(
            @PathVariable Long id,
            @RequestParam Long agenceId) {
        immobilisationService.transfererVersAgence(id, agenceId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/retirer-agence")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> retirerDeAgence(@PathVariable Long id) {
        immobilisationService.retirerDeAgence(id);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS DE STATISTIQUES =====

    @GetMapping("/stats/categorie/{categorieId}/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Long>> compterImmobilisationsParCategorie(@PathVariable Long categorieId) {
        Long count = immobilisationService.compterImmobilisationsParCategorie(categorieId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/agence/{agenceId}/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Long>> compterImmobilisationsParAgence(@PathVariable Long agenceId) {
        Long count = immobilisationService.compterImmobilisationsParAgence(agenceId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/etat/{etat}/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Long>> compterImmobilisationsParEtat(@PathVariable EtatImmobilisation etat) {
        Long count = immobilisationService.compterImmobilisationsParEtat(etat);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/dashboard")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>> obtenirStatistiquesDashboard() {
        // Statistiques générales pour le dashboard
        Map<String, Object> stats = Map.of(
                "totalActives", immobilisationService.obtenirImmobilisationsActives().size(),
                "enService", immobilisationService.compterImmobilisationsParEtat(EtatImmobilisation.EN_SERVICE),
                "enPanne", immobilisationService.compterImmobilisationsParEtat(EtatImmobilisation.EN_PANNE),
                "enReparation", immobilisationService.compterImmobilisationsParEtat(EtatImmobilisation.EN_REPARATION),
                "miseAuRebus", immobilisationService.compterImmobilisationsParEtat(EtatImmobilisation.MISE_AU_REBUS)
        );
        return ResponseEntity.ok(stats);
    }

    // ===== ENDPOINTS UTILITAIRES =====

    @GetMapping("/etats")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EtatImmobilisation[]> obtenirTousLesEtats() {
        return ResponseEntity.ok(EtatImmobilisation.values());
    }

    @GetMapping("/etats-avec-libelles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<EtatImmobilisation, String>> obtenirEtatsAvecLibelles() {
        Map<EtatImmobilisation, String> etatsAvecLibelles = Map.of(
                EtatImmobilisation.EN_SERVICE, EtatImmobilisation.EN_SERVICE.getLibelle(),
                EtatImmobilisation.EN_PANNE, EtatImmobilisation.EN_PANNE.getLibelle(),
                EtatImmobilisation.EN_REPARATION, EtatImmobilisation.EN_REPARATION.getLibelle(),
                EtatImmobilisation.MISE_AU_REBUS, EtatImmobilisation.MISE_AU_REBUS.getLibelle()
        );
        return ResponseEntity.ok(etatsAvecLibelles);
    }

    // ===== ENDPOINTS DE VALIDATION =====

    @GetMapping("/validate/numero-serie")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    public ResponseEntity<Map<String, Boolean>> validerNumeroSerie(
            @RequestParam String numeroSerie,
            @RequestParam(required = false) Long excludeId) {
        return ResponseEntity.ok(Map.of("available",
                immobilisationService.numeroSerieDisponible(numeroSerie, excludeId)));
    }

    @GetMapping("/validate/code")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    public ResponseEntity<Map<String, Boolean>> validerCodeImmobilisation(
            @RequestParam String code,
            @RequestParam(required = false) Long excludeId) {
        return ResponseEntity.ok(Map.of("available",
                immobilisationService.codeImmobilisationDisponible(code, excludeId)));
    }

    @GetMapping("/search-light")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ImmobilisationResponseDto>> searchByTerm(
            @RequestParam String term
    ){
        return ResponseEntity.ok(immobilisationService.searchActiveByTerm(term));
    }

    @GetMapping("/{id}/interventions")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    @Operation(summary = "Obtenir l'historique des interventions pour une immobilisation")
    public ResponseEntity<List<InterventionResponseDto>> getInterventionsForImmobilisation(@PathVariable Long id) {
       // log.info("Requête pour l'historique des interventions de l'immobilisation ID: {}", id);
        List<InterventionResponseDto> interventions = immobilisationService.findInterventionsByImmobilisationId(id);
        return ResponseEntity.ok(interventions);
    }

    @GetMapping("/{id}/transactions")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Obtenir l'historique des transactions pour une immobilisation")
    public ResponseEntity<List<TransactionResponseDto>> getTransactionsForImmobilisation(@PathVariable Long id) {
       // log.info("Requête pour l'historique des transactions de l'immobilisation ID: {}", id);
        List<TransactionResponseDto> transactions = immobilisationService.findTransactionsByImmobilisationId(id);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}/etiquette")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    @Operation(summary = "Générer une étiquette en PDF pour une immobilisation")
    public ResponseEntity<byte[]> getEtiquetteForImmobilisation(@PathVariable Long id) throws IOException {

        byte[] pdfBytes = etiquetteService.genererEtiquettePdf(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "etiquette_" + id + ".pdf";

        // "inline" suggère au navigateur d'ouvrir le PDF directement, ce qui est idéal pour une impression.
        // "attachment" forcerait le téléchargement.
        headers.setContentDispositionFormData("inline", filename);

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}