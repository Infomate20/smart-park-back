package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.response.ImmobilisationResponseDto;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;
import smartPark.smart_park.services.ImmobilisationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/immobilisations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ImmobilisationController {

    private final ImmobilisationService immobilisationService;

    // ===== ENDPOINTS CRUD DE BASE =====

    @PostMapping
    public ResponseEntity<ImmobilisationResponseDto> creerImmobilisation(@Valid @RequestBody ImmobilisationRequestDto requestDto) {
        ImmobilisationResponseDto immobilisation = immobilisationService.creerImmobilisation(requestDto);
        return new ResponseEntity<>(immobilisation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImmobilisationResponseDto> obtenirImmobilisationParId(@PathVariable Long id) {
        ImmobilisationResponseDto immobilisation = immobilisationService.obtenirImmobilisationParId(id);
        return ResponseEntity.ok(immobilisation);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ImmobilisationResponseDto> obtenirImmobilisationParCode(@PathVariable String code) {
        ImmobilisationResponseDto immobilisation = immobilisationService.obtenirImmobilisationParCode(code);
        return ResponseEntity.ok(immobilisation);
    }

    @GetMapping
    public ResponseEntity<List<ImmobilisationResponseDto>> obtenirToutesLesImmobilisations() {
        List<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirToutesLesImmobilisations();
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<ImmobilisationResponseDto>> obtenirImmobilisationsActives() {
        List<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsActives();
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationAvecPagination(
            @RequestParam(required = false) String searchTerm, // <-- PARAMÈTRE AJOUTÉ
            @RequestParam(required = false) Boolean actif,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationAvecPagination( searchTerm, actif,pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImmobilisationResponseDto> modifierImmobilisation(
            @PathVariable Long id,
            @Valid @RequestBody ImmobilisationRequestDto requestDto) {
        ImmobilisationResponseDto immobilisation = immobilisationService.modifierImmobilisation(id, requestDto);
        return ResponseEntity.ok(immobilisation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerImmobilisation(@PathVariable Long id) {
        immobilisationService.supprimerImmobilisation(id);
        return ResponseEntity.noContent().build();
    }

    // ===== ENDPOINTS DE RECHERCHE =====

    @GetMapping("/search")
    public ResponseEntity<Page<ImmobilisationResponseDto>> rechercherImmobilisations(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.rechercherImmobilisations(searchTerm, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/categorie/{categorieId}")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParCategorie(
            @PathVariable Long categorieId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParCategorie(categorieId, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/agence/{agenceId}")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParAgence(
            @PathVariable Long agenceId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParAgence(agenceId, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/sans-agence")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsSansAgence(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsSansAgence(pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/etat/{etat}")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParEtat(
            @PathVariable EtatImmobilisation etat,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParEtat(etat, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/periode-acquisition")
    public ResponseEntity<List<ImmobilisationResponseDto>> obtenirImmobilisationsParPeriodeAcquisition(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        List<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParPeriodeAcquisition(dateDebut, dateFin);
        return ResponseEntity.ok(immobilisations);
    }

    @GetMapping("/plage-prix")
    public ResponseEntity<Page<ImmobilisationResponseDto>> obtenirImmobilisationsParPlagePrix(
            @RequestParam BigDecimal prixMin,
            @RequestParam BigDecimal prixMax,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<ImmobilisationResponseDto> immobilisations = immobilisationService.obtenirImmobilisationsParPlageGPrix(prixMin, prixMax, pageable);
        return ResponseEntity.ok(immobilisations);
    }

    // ===== ENDPOINTS DE GESTION D'ÉTAT =====

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Void> desactiverImmobilisation(@PathVariable Long id) {
        immobilisationService.desactiverImmobilisation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<Void> activerImmobilisation(@PathVariable Long id) {
        immobilisationService.activerImmobilisation(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/changer-etat")
    public ResponseEntity<Void> changerEtatImmobilisation(
            @PathVariable Long id,
            @RequestParam EtatImmobilisation nouvelEtat) {
        immobilisationService.changerEtatImmobilisation(id, nouvelEtat);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS DE GESTION DES AGENCES =====

    @PutMapping("/{id}/transferer-agence")
    public ResponseEntity<Void> transfererVersAgence(
            @PathVariable Long id,
            @RequestParam Long agenceId) {
        immobilisationService.transfererVersAgence(id, agenceId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/retirer-agence")
    public ResponseEntity<Void> retirerDeAgence(@PathVariable Long id) {
        immobilisationService.retirerDeAgence(id);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS DE STATISTIQUES =====

    @GetMapping("/stats/categorie/{categorieId}/count")
    public ResponseEntity<Map<String, Long>> compterImmobilisationsParCategorie(@PathVariable Long categorieId) {
        Long count = immobilisationService.compterImmobilisationsParCategorie(categorieId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/agence/{agenceId}/count")
    public ResponseEntity<Map<String, Long>> compterImmobilisationsParAgence(@PathVariable Long agenceId) {
        Long count = immobilisationService.compterImmobilisationsParAgence(agenceId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/etat/{etat}/count")
    public ResponseEntity<Map<String, Long>> compterImmobilisationsParEtat(@PathVariable EtatImmobilisation etat) {
        Long count = immobilisationService.compterImmobilisationsParEtat(etat);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/dashboard")
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
    public ResponseEntity<EtatImmobilisation[]> obtenirTousLesEtats() {
        return ResponseEntity.ok(EtatImmobilisation.values());
    }

    @GetMapping("/etats-avec-libelles")
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
    public ResponseEntity<Map<String, Boolean>> validerNumeroSerie(
            @RequestParam String numeroSerie,
            @RequestParam(required = false) Long excludeId) {
        // Vérifier si le numéro de série existe déjà
        boolean existe = (excludeId != null)
                ? immobilisationService.obtenirToutesLesImmobilisations().stream()
                .anyMatch(i -> i.getNumeroSerie().equals(numeroSerie) && !i.getId().equals(excludeId))
                : immobilisationService.obtenirToutesLesImmobilisations().stream()
                .anyMatch(i -> i.getNumeroSerie().equals(numeroSerie));

        return ResponseEntity.ok(Map.of("available", !existe));
    }

    @GetMapping("/validate/code")
    public ResponseEntity<Map<String, Boolean>> validerCodeImmobilisation(
            @RequestParam String code,
            @RequestParam(required = false) Long excludeId) {
        // Vérifier si le code existe déjà
        boolean existe = (excludeId != null)
                ? immobilisationService.obtenirToutesLesImmobilisations().stream()
                .anyMatch(i -> i.getCodeImmobilisation().equals(code) && !i.getId().equals(excludeId))
                : immobilisationService.obtenirToutesLesImmobilisations().stream()
                .anyMatch(i -> i.getCodeImmobilisation().equals(code));

        return ResponseEntity.ok(Map.of("available", !existe));
    }
}