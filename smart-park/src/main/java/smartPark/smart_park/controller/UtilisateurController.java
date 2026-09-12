package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.ChangementMotDePasseRequestDto;
import smartPark.smart_park.models.dto.request.UtilisateurRequestDto;
import smartPark.smart_park.models.dto.response.UtilisateurResponseDto;
import smartPark.smart_park.models.entity.enums.Role;
import smartPark.smart_park.services.UtilisateurService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * La gestion des comptes est réservée à l'ADMIN par défaut : l'annotation de classe
 * ci-dessous s'applique à toutes les méthodes, y compris celles ajoutées plus tard.
 * Les quelques endpoints ouverts plus largement portent leur propre @PreAuthorize,
 * qui prend le pas sur celle de la classe.
 */
@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    // ===== ENDPOINTS CRUD DE BASE =====

    @PostMapping("/create")
    public ResponseEntity<UtilisateurResponseDto> creerUtilisateur(@Valid @RequestBody UtilisateurRequestDto requestDto) {
        UtilisateurResponseDto utilisateur = utilisateurService.creerUtilisateur(requestDto);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.utilisateur.id")
    public ResponseEntity<UtilisateurResponseDto> obtenirUtilisateurParId(@PathVariable Long id) {
        UtilisateurResponseDto utilisateur = utilisateurService.obtenirUtilisateurParId(id);
        return ResponseEntity.ok(utilisateur);
    }

    @GetMapping("/nom-utilisateur/{nomUtilisateur}")
    public ResponseEntity<UtilisateurResponseDto> obtenirUtilisateurParNomUtilisateur(@PathVariable String nomUtilisateur) {
        UtilisateurResponseDto utilisateur = utilisateurService.obtenirUtilisateurParNomUtilisateur(nomUtilisateur);
        return ResponseEntity.ok(utilisateur);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UtilisateurResponseDto> obtenirUtilisateurParEmail(@PathVariable String email) {
        UtilisateurResponseDto utilisateur = utilisateurService.obtenirUtilisateurParEmail(email);
        return ResponseEntity.ok(utilisateur);
    }

    @GetMapping
    public ResponseEntity<List<UtilisateurResponseDto>> obtenirTousLesUtilisateurs() {
        List<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirTousLesUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/actifs")
    public ResponseEntity<List<UtilisateurResponseDto>> obtenirUtilisateursActifs() {
        List<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursActifs();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UtilisateurResponseDto>> obtenirUtilisateursAvecPagination(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Boolean actif,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursAvecPagination(searchTerm ,actif ,pageable);
        return ResponseEntity.ok(utilisateurs);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDto> modifierUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody UtilisateurRequestDto requestDto) {
        UtilisateurResponseDto utilisateur = utilisateurService.modifierUtilisateur(id, requestDto);
        return ResponseEntity.ok(utilisateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }

    // ===== ENDPOINTS DE RECHERCHE =====

    @GetMapping("/search")
    public ResponseEntity<Page<UtilisateurResponseDto>> rechercherUtilisateurs(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<UtilisateurResponseDto> utilisateurs = utilisateurService.rechercherUtilisateurs(searchTerm, pageable);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<Page<UtilisateurResponseDto>> obtenirUtilisateursParRole(
            @PathVariable Role role,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursParRole(role, pageable);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/agence/{agenceId}")
    public ResponseEntity<Page<UtilisateurResponseDto>> obtenirUtilisateursParAgence(
            @PathVariable Long agenceId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursParAgence(agenceId, pageable);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/sans-agence")
    public ResponseEntity<Page<UtilisateurResponseDto>> obtenirUtilisateursSansAgence(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursSansAgence(pageable);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/verrouilles")
    public ResponseEntity<Page<UtilisateurResponseDto>> obtenirUtilisateursVerrouilles(
            @PageableDefault(size = 10) Pageable pageable) {
        Page<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursVerrouilles(pageable);
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/premiere-connexion")
    public ResponseEntity<List<UtilisateurResponseDto>> obtenirUtilisateursPremiereConnexion() {
        List<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursPremiereConnexion();
        return ResponseEntity.ok(utilisateurs);
    }

    @GetMapping("/inactifs-since")
    public ResponseEntity<List<UtilisateurResponseDto>> obtenirUtilisateursInactifsSince(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateLimit) {
        List<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirUtilisateursInactifsSince(dateLimit);
        return ResponseEntity.ok(utilisateurs);
    }

    // ===== ENDPOINTS DE GESTION D'ÉTAT =====

    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Void> desactiverUtilisateur(@PathVariable Long id) {
        utilisateurService.desactiverUtilisateur(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<Void> activerUtilisateur(@PathVariable Long id) {
        utilisateurService.activerUtilisateur(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/verrouiller")
    public ResponseEntity<Void> verrouillerCompte(@PathVariable Long id) {
        utilisateurService.verrouillerCompte(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/deverrouiller")
    public ResponseEntity<Void> deverrouillerCompte(@PathVariable Long id) {
        utilisateurService.deverrouillerCompte(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/expirer-mot-de-passe")
    public ResponseEntity<Void> marquerMotDePasseExpire(@PathVariable Long id) {
        utilisateurService.marquerMotDePasseExpire(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/marquer-premiere-connexion-terminee")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.utilisateur.id")
    public ResponseEntity<Void> marquerPremiereConnexionTerminee(@PathVariable Long id) {
        utilisateurService.marquerPremiereConnexionTerminee(id);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS DE GESTION DES MOTS DE PASSE =====

    // Chacun peut changer son propre mot de passe ; l'ancien mot de passe est
    // vérifié par le service. Le reset sans ancien mot de passe reste ADMIN.
    @PutMapping("/{id}/changer-mot-de-passe")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.utilisateur.id")
    public ResponseEntity<Void> changerMotDePasse(
            @PathVariable Long id,
            @Valid @RequestBody ChangementMotDePasseRequestDto requestDto) {
        utilisateurService.changerMotDePasse(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reset-mot-de-passe")
    public ResponseEntity<Void> resetMotDePasse(
            @PathVariable Long id,
            @RequestParam String nouveauMotDePasse) {
        utilisateurService.resetMotDePasse(id, nouveauMotDePasse);
        return ResponseEntity.ok().build();
    }

    // L'authentification se fait exclusivement via /api/auth (login + OTP).

    // ===== ENDPOINTS DE GESTION DES AGENCES =====

    @PutMapping("/{id}/transferer-agence")
    public ResponseEntity<Void> transfererVersAgence(
            @PathVariable Long id,
            @RequestParam Long agenceId) {
        utilisateurService.transfererVersAgence(id, agenceId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/retirer-agence")
    public ResponseEntity<Void> retirerDeAgence(@PathVariable Long id) {
        utilisateurService.retirerDeAgence(id);
        return ResponseEntity.ok().build();
    }

    // ===== ENDPOINTS DE VALIDATION =====

    @GetMapping("/validate/nom-utilisateur")
    public ResponseEntity<Map<String, Boolean>> validerUniciteNomUtilisateur(
            @RequestParam String nomUtilisateur,
            @RequestParam(required = false) Long excludeId) {
        boolean estUnique = utilisateurService.verifierUniciteNomUtilisateur(nomUtilisateur, excludeId);
        return ResponseEntity.ok(Map.of("unique", estUnique));
    }

    @GetMapping("/validate/email")
    public ResponseEntity<Map<String, Boolean>> validerUniciteEmail(
            @RequestParam String email,
            @RequestParam(required = false) Long excludeId) {
        boolean estUnique = utilisateurService.verifierUniciteEmail(email, excludeId);
        return ResponseEntity.ok(Map.of("unique", estUnique));
    }

    @GetMapping("/validate/matricule")
    public ResponseEntity<Map<String, Boolean>> validerUniciteMatricule(
            @RequestParam String matricule,
            @RequestParam(required = false) Long excludeId) {
        boolean estUnique = utilisateurService.verifierUniciteMatricule(matricule, excludeId);
        return ResponseEntity.ok(Map.of("unique", estUnique));
    }

    @GetMapping("/{id}/peut-supprimer")
    public ResponseEntity<Map<String, Boolean>> verifierPossibiliteSuppression(@PathVariable Long id) {
        boolean peutSupprimer = utilisateurService.peutSupprimerUtilisateur(id);
        return ResponseEntity.ok(Map.of("canDelete", peutSupprimer));
    }

    // ===== ENDPOINTS DE STATISTIQUES =====

    @GetMapping("/stats/role/{role}")
    public ResponseEntity<Map<String, Long>> compterUtilisateursParRole(@PathVariable Role role) {
        Long count = utilisateurService.compterUtilisateursParRole(role);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/agence/{agenceId}")
    public ResponseEntity<Map<String, Long>> compterUtilisateursParAgence(@PathVariable Long agenceId) {
        Long count = utilisateurService.compterUtilisateursParAgence(agenceId);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/verrouilles")
    public ResponseEntity<Map<String, Long>> compterUtilisateursVerrouilles() {
        Long count = utilisateurService.compterUtilisateursVerrouilles();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/premiere-connexion")
    public ResponseEntity<Map<String, Long>> compterUtilisateursPremiereConnexion() {
        Long count = utilisateurService.compterUtilisateursPremiereConnexion();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/stats/dashboard")
    public ResponseEntity<Map<String, Long>> obtenirStatistiquesUtilisateurs() {
        Map<String, Long> statistiques = utilisateurService.obtenirStatistiquesUtilisateurs();
        return ResponseEntity.ok(statistiques);
    }

    // ===== ENDPOINTS UTILITAIRES =====

    @GetMapping("/roles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Role[]> obtenirTousLesRoles() {
        return ResponseEntity.ok(Role.values());
    }

    @GetMapping("/roles-avec-libelles")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<Role, String>> obtenirRolesAvecLibelles() {
        Map<Role, String> rolesAvecLibelles = Map.of(
                Role.ADMIN, Role.ADMIN.getLibelle(),
                Role.TECHNICIEN, Role.TECHNICIEN.getLibelle(),
                Role.AGENT, Role.AGENT.getLibelle()
        );
        return ResponseEntity.ok(rolesAvecLibelles);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exporterUtilisateursCSV() {
        List<UtilisateurResponseDto> utilisateurs = utilisateurService.obtenirTousLesUtilisateurs();

        StringBuilder csv = new StringBuilder();
        csv.append("ID,Nom Utilisateur,Nom,Prénom,Email,Rôle,Agence,Actif,Date Création\n");

        for (UtilisateurResponseDto utilisateur : utilisateurs) {
            csv.append(utilisateur.getId()).append(",")
                    .append(utilisateur.getNomUtilisateur()).append(",")
                    .append("\"").append(",")
                    .append("\"").append(utilisateur.getPrenom()).append("\"").append(",")
                    .append(utilisateur.getEmail() != null ? utilisateur.getEmail() : "").append(",")
                    .append(utilisateur.getRoleLibelle()).append(",")
                    .append(utilisateur.getAgenceNom() != null ? utilisateur.getAgenceNom() : "").append(",")
                    .append(utilisateur.getActif()).append(",")
                    .append(utilisateur.getDateCreation())
                    .append("\n");
        }

        return ResponseEntity.ok()
                .header("Content-Type", "text/csv")
                .header("Content-Disposition", "attachment; filename=\"utilisateurs.csv\"")
                .body(csv.toString());
    }

    // ===== ENDPOINTS DE GESTION EN LOT =====

    @PutMapping("/batch/activer")
    public ResponseEntity<Map<String, Object>> activerPlusieursUtilisateurs(@RequestBody List<Long> ids) {
        int actives = 0;
        int erreurs = 0;

        for (Long id : ids) {
            try {
                utilisateurService.activerUtilisateur(id);
                actives++;
            } catch (Exception e) {
                erreurs++;
            }
        }

        Map<String, Object> resultat = Map.of(
                "traites", ids.size(),
                "actives", actives,
                "erreurs", erreurs
        );

        return ResponseEntity.ok(resultat);
    }

    @PutMapping("/batch/desactiver")
    public ResponseEntity<Map<String, Object>> desactiverPlusieursUtilisateurs(@RequestBody List<Long> ids) {
        int desactives = 0;
        int erreurs = 0;

        for (Long id : ids) {
            try {
                utilisateurService.desactiverUtilisateur(id);
                desactives++;
            } catch (Exception e) {
                erreurs++;
            }
        }

        Map<String, Object> resultat = Map.of(
                "traites", ids.size(),
                "desactives", desactives,
                "erreurs", erreurs
        );

        return ResponseEntity.ok(resultat);
    }

//    @PutMapping("/batch/deverrouiller")
//    public ResponseEntity<Map<String, Object>> deverrouillerPlusieursUtilisateurs(@RequestBody List<Long> ids) {
//        int deverrouilles = 0;
//        int erreurs = 0;
//
//        for (Long id : ids) {
//            try {
//                utilisateurService.deverrouilerCompte(id);
//                deverrouilles++;
//            } catch (Exception e) {
//                erreurs++;
//            }
//        }
//
//        Map<String, Object> resultat = Map.of(
//                "traites", ids.size(),
//                "deverrouilles", deverrouilles,
//                "erreurs", erreurs
//        );
//
//        return ResponseEntity.ok(resultat);
//    }

    // ===== ENDPOINTS DE RAPPORTS =====

    @GetMapping("/rapport/activite")
    public ResponseEntity<Map<String, Object>> obtenirRapportActivite() {
        Map<String, Long> statistiques = utilisateurService.obtenirStatistiquesUtilisateurs();
        List<UtilisateurResponseDto> utilisateursActifs = utilisateurService.obtenirUtilisateursActifs();

        // Calculer la dernière connexion moyenne
        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime limiteInactivite = maintenant.minusDays(30);
        List<UtilisateurResponseDto> inactifs = utilisateurService.obtenirUtilisateursInactifsSince(limiteInactivite);

        Map<String, Object> rapport = Map.of(
                "statistiquesGenerales", statistiques,
                "utilisateursConnectesRecemment", utilisateursActifs.size() - inactifs.size(),
                "utilisateursInactifs30Jours", inactifs.size(),
                "tauxActivite", utilisateursActifs.size() > 0 ?
                        ((double)(utilisateursActifs.size() - inactifs.size()) / utilisateursActifs.size()) * 100 : 0
        );

        return ResponseEntity.ok(rapport);
    }

    @GetMapping("/rapport/securite")
    public ResponseEntity<Map<String, Object>> obtenirRapportSecurite() {
        Map<String, Long> statistiques = utilisateurService.obtenirStatistiquesUtilisateurs();

        Map<String, Object> rapport = Map.of(
                "comptesVerrouilles", statistiques.get("verrouilles"),
                "premiereConnexion", statistiques.get("premiereConnexion"),
                "pourcentageComptesSecurises", statistiques.get("actifs") > 0 ?
                        ((double)(statistiques.get("actifs") - statistiques.get("verrouilles")) / statistiques.get("actifs")) * 100 : 0,
                "recommandations", List.of(
                        "Vérifier les comptes verrouillés",
                        "Accompagner les utilisateurs en première connexion",
                        "Mettre en place des politiques de mot de passe"
                )
        );

        return ResponseEntity.ok(rapport);
    }
}