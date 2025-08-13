package smartPark.smart_park.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.request.TransactionRequestDto;
import smartPark.smart_park.models.dto.request.TransactionUpdateDto;
import smartPark.smart_park.models.dto.request.TransactionValidationDto;
import smartPark.smart_park.models.dto.response.TransactionResponseDto;
import smartPark.smart_park.models.entity.enums.EtatTransaction;
import smartPark.smart_park.models.entity.enums.TypeTransaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    /**
     * Créer une nouvelle transaction
     */
    TransactionResponseDto creerTransaction(TransactionRequestDto requestDto);

    /**
     * Récupérer une transaction par son ID
     */
    TransactionResponseDto obtenirTransactionParId(Long id);

    /**
     * Récupérer toutes les transactions avec pagination
     */
    Page<TransactionResponseDto> obtenirToutesLesTransactions(Pageable pageable);

    /**
     * Récupérer toutes les transactions sans pagination
     */
    List<TransactionResponseDto> obtenirToutesLesTransactions();

    /**
     * Mettre à jour une transaction (seulement si en attente)
     */
    TransactionResponseDto mettreAJourTransaction(Long id, TransactionUpdateDto updateDto);

    /**
     * Supprimer une transaction
     */
    void supprimerTransaction(Long id);

    /**
     * Valider ou rejeter une transaction
     */
    TransactionResponseDto validerTransaction(Long id, TransactionValidationDto validationDto);

    /**
     * Récupérer les transactions par immobilisation
     */
    List<TransactionResponseDto> obtenirTransactionsParImmobilisation(Long immobilisationId);

    /**
     * Récupérer les transactions par immobilisation avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParImmobilisation(Long immobilisationId, Pageable pageable);

    /**
     * Récupérer les transactions par demandeur
     */
    List<TransactionResponseDto> obtenirTransactionsParDemandeur(Long demandeurId);

    /**
     * Récupérer les transactions par demandeur avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParDemandeur(Long demandeurId, Pageable pageable);

    /**
     * Récupérer les transactions par validateur
     */
    List<TransactionResponseDto> obtenirTransactionsParValidateur(Long validateurId);

    /**
     * Récupérer les transactions par validateur avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParValidateur(Long validateurId, Pageable pageable);

    /**
     * Récupérer les transactions par état
     */
    List<TransactionResponseDto> obtenirTransactionsParEtat(EtatTransaction etatTransaction);

    /**
     * Récupérer les transactions par état avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParEtat(EtatTransaction etatTransaction, Pageable pageable);

    /**
     * Récupérer les transactions par type
     */
    List<TransactionResponseDto> obtenirTransactionsParType(TypeTransaction typeTransaction);

    /**
     * Récupérer les transactions par type avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParType(TypeTransaction typeTransaction, Pageable pageable);

    /**
     * Récupérer les transactions par agence source
     */
    List<TransactionResponseDto> obtenirTransactionsParAgenceSource(Long agenceSourceId);

    /**
     * Récupérer les transactions par agence source avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParAgenceSource(Long agenceSourceId, Pageable pageable);

    /**
     * Récupérer les transactions par agence destination
     */
    List<TransactionResponseDto> obtenirTransactionsParAgenceDestination(Long agenceDestinationId);

    /**
     * Récupérer les transactions par agence destination avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParAgenceDestination(Long agenceDestinationId, Pageable pageable);

    /**
     * Récupérer les transactions impliquant une agence (source ou destination)
     */
    List<TransactionResponseDto> obtenirTransactionsParAgenceImpliquee(Long agenceId);

    /**
     * Récupérer les transactions impliquant une agence avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsParAgenceImpliquee(Long agenceId, Pageable pageable);

    /**
     * Récupérer les transactions par période de demande
     */
    List<TransactionResponseDto> obtenirTransactionsParPeriodeDemande(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Récupérer les transactions par période de validation
     */
    List<TransactionResponseDto> obtenirTransactionsParPeriodeValidation(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Récupérer les transactions en attente
     */
    List<TransactionResponseDto> obtenirTransactionsEnAttente();

    /**
     * Récupérer les transactions en attente avec pagination
     */
    Page<TransactionResponseDto> obtenirTransactionsEnAttente(Pageable pageable);

    /**
     * Recherche avec critères multiples
     */
    Page<TransactionResponseDto> rechercherAvecCriteres(
            Long immobilisationId,
            Long demandeurId,
            Long validateurId,
            EtatTransaction etatTransaction,
            TypeTransaction typeTransaction,
            Long agenceSourceId,
            Long agenceDestinationId,
            LocalDateTime dateDebut,
            LocalDateTime dateFin,
            Pageable pageable
    );

    /**
     * Récupérer les transactions récentes d'une immobilisation
     */
    List<TransactionResponseDto> obtenirTransactionsRecentes(Long immobilisationId, int limite);

    /**
     * Obtenir les statistiques par état de transaction
     */
    List<Object[]> obtenirStatistiquesParEtat();

    /**
     * Obtenir les statistiques par type de transaction
     */
    List<Object[]> obtenirStatistiquesParType();

    /**
     * Vérifier si une transaction existe
     */
    boolean transactionExiste(Long id);

    /**
     * Vérifier si une immobilisation a des transactions en attente
     */
    boolean hasTransactionsEnAttenteForImmobilisation(Long immobilisationId);

    /**
     * Annuler une transaction (seulement si en attente)
     */
    TransactionResponseDto annulerTransaction(Long id, Long utilisateurId);
}