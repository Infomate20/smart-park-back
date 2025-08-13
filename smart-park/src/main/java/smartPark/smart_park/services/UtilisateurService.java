package smartPark.smart_park.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.request.ChangementMotDePasseRequestDto;
import smartPark.smart_park.models.dto.request.ConnexionRequestDto;
import smartPark.smart_park.models.dto.request.UtilisateurRequestDto;
import smartPark.smart_park.models.dto.response.UtilisateurResponseDto;
import smartPark.smart_park.models.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UtilisateurService {

    // CRUD de base
    UtilisateurResponseDto creerUtilisateur(UtilisateurRequestDto requestDto);

    UtilisateurResponseDto obtenirUtilisateurParId(Long id);

    UtilisateurResponseDto obtenirUtilisateurParNomUtilisateur(String nomUtilisateur);

    UtilisateurResponseDto obtenirUtilisateurParEmail(String email);

    List<UtilisateurResponseDto> obtenirTousLesUtilisateurs();

    List<UtilisateurResponseDto> obtenirUtilisateursActifs();

    Page<UtilisateurResponseDto> obtenirUtilisateursAvecPagination(String SeachTerm,Boolean actif, Pageable pageable);


    Page<UtilisateurResponseDto> rechercherUtilisateurs(String searchTerm, Pageable pageable);

    UtilisateurResponseDto modifierUtilisateur(Long id, UtilisateurRequestDto requestDto);

    void supprimerUtilisateur(Long id);

    void desactiverUtilisateur(Long id);

    void activerUtilisateur(Long id);

    // Recherches spécialisées
    Page<UtilisateurResponseDto> obtenirUtilisateursParRole(Role role, Pageable pageable);

    Page<UtilisateurResponseDto> obtenirUtilisateursParAgence(Long agenceId, Pageable pageable);

    Page<UtilisateurResponseDto> obtenirUtilisateursSansAgence(Pageable pageable);

    Page<UtilisateurResponseDto> obtenirUtilisateursVerrouilles(Pageable pageable);

    List<UtilisateurResponseDto> obtenirUtilisateursPremiereConnexion();

    // Gestion des mots de passe
    void changerMotDePasse(Long id, ChangementMotDePasseRequestDto requestDto);

    void resetMotDePasse(Long id, String nouveauMotDePasse);

    void marquerMotDePasseExpire(Long id);

    // Gestion de la connexion
    UtilisateurResponseDto authentifier(ConnexionRequestDto requestDto);

    void enregistrerConnexion(Long id);

    void incrementerTentativesEchouees(Long id);

    void resetTentativesEchouees(Long id);

    void verrouillerCompte(Long id);

    void deverrouillerCompte(Long id);

    void marquerPremiereConnexionTerminee(Long id);

    // Gestion des agences
    void transfererVersAgence(Long utilisateurId, Long agenceId);

    void retirerDeAgence(Long utilisateurId);

    // Validations
    boolean verifierUniciteNomUtilisateur(String nomUtilisateur, Long excludeId);

    boolean verifierUniciteEmail(String email, Long excludeId);

    boolean verifierUniciteMatricule(String matricule, Long excludeId);

    // Statistiques
    Long compterUtilisateursParRole(Role role);

    Long compterUtilisateursParAgence(Long agenceId);

    Long compterUtilisateursVerrouilles();

    Long compterUtilisateursPremiereConnexion();

    Map<String, Long> obtenirStatistiquesUtilisateurs();

    // Utilitaires
    List<UtilisateurResponseDto> obtenirUtilisateursInactifsSince(LocalDateTime dateLimit);

    boolean peutSupprimerUtilisateur(Long utilisateurId);
}
