package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.AgenceRequestDto;
import smartPark.smart_park.models.dto.response.AgenceResponseDto;
import smartPark.smart_park.services.AgenceService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AgenceController {

    private final AgenceService agenceService;

    // ===== ENDPOINTS CRUD DE BASE =====

    @PostMapping
    public ResponseEntity<AgenceResponseDto> creerAgence(@Valid @RequestBody AgenceRequestDto requestDto) {
        AgenceResponseDto agence = agenceService.creerAgence(requestDto);
        return new ResponseEntity<>(agence, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenceResponseDto> obtenirAgenceParId(@PathVariable Long id) {
        AgenceResponseDto agence = agenceService.obtenirAgenceParId(id);
        return ResponseEntity.ok(agence);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<AgenceResponseDto> obtenirAgenceParCode(@PathVariable String code) {
        AgenceResponseDto agence = agenceService.obtenirAgenceParCode(code);
        return ResponseEntity.ok(agence);
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<AgenceResponseDto> obtenirAgenceParNom(@PathVariable String nom) {
        AgenceResponseDto agence = agenceService.obtenirAgenceParNom(nom);
        return ResponseEntity.ok(agence);
    }

    @GetMapping
    public ResponseEntity<List<AgenceResponseDto>> obtenirToutesLesAgences() {
        List<AgenceResponseDto> agences = agenceService.obtenirToutesLesAgences();
        return ResponseEntity.ok(agences);
    }

    @GetMapping("/actives")
    public ResponseEntity<List<AgenceResponseDto>> obtenirAgencesActives() {
        List<AgenceResponseDto> agences = agenceService.obtenirAgencesActives();
        return ResponseEntity.ok(agences);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AgenceResponseDto>> obtenirAgencesAvecPagination(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Boolean actif,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<AgenceResponseDto> agences = agenceService.obtenirAgencesAvecPagination(searchTerm,actif,pageable);
        return ResponseEntity.ok(agences);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgenceResponseDto> modifierAgence(
            @PathVariable Long id,
            @Valid @RequestBody AgenceRequestDto requestDto) {
        AgenceResponseDto agence = agenceService.modifierAgence(id, requestDto);
        return ResponseEntity.ok(agence);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAgence(@PathVariable Long id) {
        agenceService.supprimerAgence(id);
        return ResponseEntity.noContent().build();
    }

    // ===== ENDPOINTS DE RECHERCHE =====

    @GetMapping("/search")
    public ResponseEntity<Page<AgenceResponseDto>> rechercherAgences(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<AgenceResponseDto> agences = agenceService.rechercherAgences(searchTerm, pageable);
        return ResponseEntity.ok(agences);
    }

    @GetMapping("/ville/{ville}")
    public ResponseEntity<List<AgenceResponseDto>> obtenirAgencesParVille(@PathVariable String ville) {
        List<AgenceResponseDto> agences = agenceService.obtenirAgencesParVille(ville);
        return ResponseEntity.ok(agences);
    }

    @GetMapping("/pays/{pays}")
    public ResponseEntity<List<AgenceResponseDto>> obtenirAgencesParPays(@PathVariable String pays) {
        List<AgenceResponseDto> agences = agenceService.obtenirAgencesParPays(pays);
        return ResponseEntity.ok(agences);
    }

    @GetMapping("/ville/{ville}/paginated")
    public ResponseEntity<Page<AgenceResponseDto>> obtenirAgencesParVilleAvecPagination(
            @PathVariable String ville,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<AgenceResponseDto> agences = agenceService.obtenirAgencesParVilleAvecPagination(ville, pageable);
        return ResponseEntity.ok(agences);
    }

    @GetMapping("/pays/{pays}/paginated")
    public ResponseEntity<Page<AgenceResponseDto>> obtenirAgencesParPaysAvecPagination(
            @PathVariable String pays,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<AgenceResponseDto> agences = agenceService.obtenirAgencesParPaysAvecPagination(pays, pageable);
        return ResponseEntity.ok(agences);
    }

    // ===== ENDPOINTS DE GESTION D'ÉTAT =====

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Void> desactiverAgence(@PathVariable Long id) {
        agenceService.desactiverAgence(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<Void> activerAgence(@PathVariable Long id) {
        agenceService.activerAgence(id);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS DE VALIDATION =====

    @GetMapping("/validate/code")
    public ResponseEntity<Map<String, Boolean>> validerUniciteCode(
            @RequestParam String code,
            @RequestParam(required = false) Long excludeId) {
        boolean estUnique = agenceService.verifierUniciteCode(code, excludeId);
        return ResponseEntity.ok(Map.of("unique", estUnique));
    }

    @GetMapping("/validate/nom")
    public ResponseEntity<Map<String, Boolean>> validerUniciteNom(
            @RequestParam String nom,
            @RequestParam(required = false) Long excludeId) {
        boolean estUnique = agenceService.verifierUniciteNom(nom, excludeId);
        return ResponseEntity.ok(Map.of("unique", estUnique));
    }

    @GetMapping("/validate/email")
    public ResponseEntity<Map<String, Boolean>> validerUniciteEmail(
            @RequestParam String email,
            @RequestParam(required = false) Long excludeId) {
        boolean estUnique = agenceService.verifierUniciteEmail(email, excludeId);
        return ResponseEntity.ok(Map.of("unique", estUnique));
    }

    @GetMapping("/{id}/peut-supprimer")
    public ResponseEntity<Map<String, Boolean>> verifierPossibiliteSuppression(@PathVariable Long id) {
        boolean peutSupprimer = agenceService.peutSupprimerAgence(id);
        return ResponseEntity.ok(Map.of("canDelete", peutSupprimer));
    }

    // ===== ENDPOINTS DE STATISTIQUES =====

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Long>> obtenirStatistiquesAgence(@PathVariable Long id) {
        Map<String, Long> statistiques = agenceService.obtenirStatistiquesAgence(id);
        return ResponseEntity.ok(statistiques);
    }

    @GetMapping("/stats/immobilisations/{id}")
    public ResponseEntity<Map<String, Long>> compterImmobilisationsParAgence(@PathVariable Long id) {
        Long count = agenceService.compterImmobilisationsParAgence(id);
        return ResponseEntity.ok(Map.of("nombreImmobilisations", count));
    }

    @GetMapping("/stats/utilisateurs/{id}")
    public ResponseEntity<Map<String, Long>> compterUtilisateursParAgence(@PathVariable Long id) {
        Long count = agenceService.compterUtilisateursParAgence(id);
        return ResponseEntity.ok(Map.of("nombreUtilisateurs", count));
    }

    @GetMapping("/stats/dashboard")
    public ResponseEntity<Map<String, Object>> obtenirStatistiquesGenerales() {
        Map<String, Object> statistiques = agenceService.obtenirStatistiquesGenerales();
        return ResponseEntity.ok(statistiques);
    }

    // ===== ENDPOINTS UTILITAIRES =====

    @GetMapping("/villes")
    public ResponseEntity<List<String>> obtenirVillesDisponibles() {
        List<String> villes = agenceService.obtenirVillesDisponibles();
        return ResponseEntity.ok(villes);
    }

    @GetMapping("/pays")
    public ResponseEntity<List<String>> obtenirPaysDisponibles() {
        List<String> pays = agenceService.obtenirPaysDisponibles();
        return ResponseEntity.ok(pays);
    }

    // ===== ENDPOINTS D'INFORMATION =====

    @GetMapping("/info/total")
    public ResponseEntity<Map<String, Long>> obtenirNombreTotalAgences() {
        List<AgenceResponseDto> agences = agenceService.obtenirToutesLesAgences();
        List<AgenceResponseDto> agencesActives = agenceService.obtenirAgencesActives();

        Map<String, Long> info = Map.of(
                "total", (long) agences.size(),
                "actives", (long) agencesActives.size(),
                "inactives", (long) (agences.size() - agencesActives.size())
        );

        return ResponseEntity.ok(info);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exporterAgencesCSV() {
        List<AgenceResponseDto> agences = agenceService.obtenirToutesLesAgences();

        StringBuilder csv = new StringBuilder();
        csv.append("ID,Code,Nom,Ville,Pays,Email,Actif,Date Creation\n");

        for (AgenceResponseDto agence : agences) {
            csv.append(agence.getId()).append(",")
                    .append(agence.getCode()).append(",")
                    .append("\"").append(agence.getNom()).append("\"").append(",")
                    .append(agence.getVille() != null ? agence.getVille() : "").append(",")
                    .append(agence.getPays() != null ? agence.getPays() : "").append(",")
                    .append(agence.getEmail() != null ? agence.getEmail() : "").append(",")
                    .append(agence.getActif()).append(",")
                    .append(agence.getDateCreation())
                    .append("\n");
        }

        return ResponseEntity.ok()
                .header("Content-Type", "text/csv")
                .header("Content-Disposition", "attachment; filename=\"agences.csv\"")
                .body(csv.toString());
    }

    // ===== ENDPOINTS DE GESTION EN LOT =====

    @PutMapping("/batch/activer")
    public ResponseEntity<Map<String, Object>> activerPlusieursAgences(@RequestBody List<Long> ids) {
        int activees = 0;
        int erreurs = 0;

        for (Long id : ids) {
            try {
                agenceService.activerAgence(id);
                activees++;
            } catch (Exception e) {
                erreurs++;
            }
        }

        Map<String, Object> resultat = Map.of(
                "traites", ids.size(),
                "activees", activees,
                "erreurs", erreurs
        );

        return ResponseEntity.ok(resultat);
    }

    @PutMapping("/batch/desactiver")
    public ResponseEntity<Map<String, Object>> desactiverPlusieursAgences(@RequestBody List<Long> ids) {
        int desactivees = 0;
        int erreurs = 0;

        for (Long id : ids) {
            try {
                agenceService.desactiverAgence(id);
                desactivees++;
            } catch (Exception e) {
                erreurs++;
            }
        }

        Map<String, Object> resultat = Map.of(
                "traites", ids.size(),
                "desactivees", desactivees,
                "erreurs", erreurs
        );

        return ResponseEntity.ok(resultat);
    }

    // ===== ENDPOINTS DE RECHERCHE AVANCÉE =====

    @PostMapping("/search/advanced")
    public ResponseEntity<Page<AgenceResponseDto>> rechercheAvancee(
            @RequestBody Map<String, Object> criteria,
            @PageableDefault(size = 10) Pageable pageable) {

        // Pour l'instant, on utilise la recherche simple avec le terme general
        String searchTerm = (String) criteria.getOrDefault("searchTerm", "");

        if (searchTerm.isEmpty()) {
            // Si aucun terme de recherche, retourner toutes les agences actives
            return ResponseEntity.ok(agenceService.obtenirAgencesAvecPaginationA(pageable));
        }

        return ResponseEntity.ok(agenceService.rechercherAgences(searchTerm, pageable));
    }

    @GetMapping("/rapport/resume")
    public ResponseEntity<Map<String, Object>> obtenirRapportResume() {
        Map<String, Object> statistiques = agenceService.obtenirStatistiquesGenerales();
        List<AgenceResponseDto> agencesActives = agenceService.obtenirAgencesActives();

        // Calculer des statistiques supplémentaires
        long totalImmobilisations = 0;
        long totalUtilisateurs = 0;

        for (AgenceResponseDto agence : agencesActives) {
            totalImmobilisations += agence.getNombreImmobilisations();
            totalUtilisateurs += agence.getNombreUtilisateurs();
        }

        Map<String, Object> rapport = Map.of(
                "statistiquesGenerales", statistiques,
                "totalImmobilisationsGeres", totalImmobilisations,
                "totalUtilisateursGeres", totalUtilisateurs,
                "moyenneImmobilisationsParAgence",
                agencesActives.size() > 0 ? totalImmobilisations / agencesActives.size() : 0,
                "moyenneUtilisateursParAgence",
                agencesActives.size() > 0 ? totalUtilisateurs / agencesActives.size() : 0
        );

        return ResponseEntity.ok(rapport);
    }
}