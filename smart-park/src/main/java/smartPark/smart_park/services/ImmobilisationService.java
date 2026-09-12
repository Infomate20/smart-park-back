package smartPark.smart_park.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.request.InterventionRequestDto;
import smartPark.smart_park.models.dto.response.ImmobilisationLightDto;
import smartPark.smart_park.models.dto.response.ImmobilisationResponseDto;
import smartPark.smart_park.models.dto.response.InterventionResponseDto;
import smartPark.smart_park.models.dto.response.TransactionResponseDto;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ImmobilisationService {

    ImmobilisationResponseDto creerImmobilisation(ImmobilisationRequestDto requestDto);

    ImmobilisationResponseDto obtenirImmobilisationParId(Long id);

    ImmobilisationResponseDto obtenirImmobilisationParCode(String codeImmobilisation);

    List<ImmobilisationResponseDto> obtenirToutesLesImmobilisations();

    List<ImmobilisationResponseDto> obtenirImmobilisationsActives();

    List<ImmobilisationResponseDto>searchActiveByTerm(String term);

    Page<ImmobilisationResponseDto> obtenirImmobilisationsAvecPagination(Pageable pageable);

    Page<ImmobilisationResponseDto> obtenirImmobilisationAvecPagination(String searchTerm, Boolean actif, Pageable pageable);

    Page<ImmobilisationResponseDto> rechercherImmobilisations(String searchTerm, Pageable pageable);

    Page<ImmobilisationResponseDto> obtenirImmobilisationsParCategorie(Long categorieId, Pageable pageable);

    Page<ImmobilisationResponseDto> obtenirImmobilisationsParAgence(Long agenceId, Pageable pageable);

    Page<ImmobilisationResponseDto> obtenirImmobilisationsSansAgence(Pageable pageable);

    Page<ImmobilisationResponseDto> obtenirImmobilisationsParEtat(EtatImmobilisation etat, Pageable pageable);

    List<ImmobilisationResponseDto> obtenirImmobilisationsParPeriodeAcquisition(LocalDate dateDebut, LocalDate dateFin);

    Page<ImmobilisationResponseDto> obtenirImmobilisationsParPlageGPrix(BigDecimal prixMin, BigDecimal prixMax, Pageable pageable);

    ImmobilisationResponseDto modifierImmobilisation(Long id, ImmobilisationRequestDto requestDto);

    void supprimerImmobilisation(Long id);

    void desactiverImmobilisation(Long id);

    void activerImmobilisation(Long id);

    void changerEtatImmobilisation(Long id, EtatImmobilisation nouvelEtat);

    void transfererVersAgence(Long immobilisationId, Long agenceId);

    void retirerDeAgence(Long immobilisationId);

    // Méthodes de statistiques
    Long compterImmobilisationsParCategorie(Long categorieId);

    Long compterImmobilisationsParAgence(Long agenceId);

    Long compterImmobilisationsParEtat(EtatImmobilisation etat);

    List<InterventionResponseDto> findInterventionsByImmobilisationId(Long immobilisationId);
    List<TransactionResponseDto> findTransactionsByImmobilisationId(Long immobilisationId);

    // Contrôles de disponibilité (saisie de formulaire) : résolus par une requête
    // d'existence, jamais en parcourant le parc
    boolean numeroSerieDisponible(String numeroSerie, Long excludeId);

    boolean codeImmobilisationDisponible(String codeImmobilisation, Long excludeId);


}