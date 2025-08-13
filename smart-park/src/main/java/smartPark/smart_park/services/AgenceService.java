package smartPark.smart_park.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.request.AgenceRequestDto;
import smartPark.smart_park.models.dto.response.AgenceResponseDto;

import java.util.List;
import java.util.Map;

public interface AgenceService {

    AgenceResponseDto creerAgence(AgenceRequestDto requestDto);

    AgenceResponseDto obtenirAgenceParId(Long id);

    AgenceResponseDto obtenirAgenceParCode(String code);

    AgenceResponseDto obtenirAgenceParNom(String nom);

    List<AgenceResponseDto> obtenirToutesLesAgences();

    List<AgenceResponseDto> obtenirAgencesActives();

    Page<AgenceResponseDto> obtenirAgencesAvecPagination(String searchTerm, Boolean actif,Pageable pageable);

    Page<AgenceResponseDto> obtenirAgencesAvecPaginationA(Pageable pageable);

    Page<AgenceResponseDto> rechercherAgences(String searchTerm, Pageable pageable);

    List<AgenceResponseDto> obtenirAgencesParVille(String ville);

    List<AgenceResponseDto> obtenirAgencesParPays(String pays);

    Page<AgenceResponseDto> obtenirAgencesParVilleAvecPagination(String ville, Pageable pageable);

    Page<AgenceResponseDto> obtenirAgencesParPaysAvecPagination(String pays, Pageable pageable);


    AgenceResponseDto modifierAgence(Long id, AgenceRequestDto requestDto);

    void supprimerAgence(Long id);

    void desactiverAgence(Long id);

    void activerAgence(Long id);

    // Méthodes de validation
    boolean verifierUniciteCode(String code, Long excludeId);

    boolean verifierUniciteNom(String nom, Long excludeId);

    boolean verifierUniciteEmail(String email, Long excludeId);

    // Méthodes de statistiques
    Long compterImmobilisationsParAgence(Long agenceId);

    Long compterUtilisateursParAgence(Long agenceId);

    Map<String, Long> obtenirStatistiquesAgence(Long agenceId);

    Map<String, Object> obtenirStatistiquesGenerales();

    // Méthodes utilitaires
    List<String> obtenirVillesDisponibles();

    List<String> obtenirPaysDisponibles();

    boolean peutSupprimerAgence(Long agenceId);
}
