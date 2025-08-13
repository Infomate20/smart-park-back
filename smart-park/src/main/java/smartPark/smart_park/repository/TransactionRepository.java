package smartPark.smart_park.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartPark.smart_park.models.entity.Transaction;
import smartPark.smart_park.models.entity.enums.EtatTransaction;
import smartPark.smart_park.models.entity.enums.TypeTransaction;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Recherche par immobilisation
    List<Transaction> findByImmobilisationId(Long immobilisationId);

    Page<Transaction> findByImmobilisationId(Long immobilisationId, Pageable pageable);

    // Recherche par demandeur
    List<Transaction> findByDemandeurId(Long demandeurId);

    Page<Transaction> findByDemandeurId(Long demandeurId, Pageable pageable);

    // Recherche par validateur
    List<Transaction> findByValidateurId(Long validateurId);

    Page<Transaction> findByValidateurId(Long validateurId, Pageable pageable);

    // Recherche par état
    List<Transaction> findByEtatTransaction(EtatTransaction etatTransaction);

    Page<Transaction> findByEtatTransaction(EtatTransaction etatTransaction, Pageable pageable);

    // Recherche par type
    List<Transaction> findByTypeTransaction(TypeTransaction typeTransaction);

    Page<Transaction> findByTypeTransaction(TypeTransaction typeTransaction, Pageable pageable);

    // Recherche par agence source
    List<Transaction> findByAgenceSourceId(Long agenceSourceId);

    Page<Transaction> findByAgenceSourceId(Long agenceSourceId, Pageable pageable);

    // Recherche par agence destination
    List<Transaction> findByAgenceDestinationId(Long agenceDestinationId);

    Page<Transaction> findByAgenceDestinationId(Long agenceDestinationId, Pageable pageable);

    // Recherche par période de demande
    @Query("SELECT t FROM Transaction t WHERE t.dateDemande BETWEEN :dateDebut AND :dateFin")
    List<Transaction> findByDateDemandeBetween(
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin
    );

    // Recherche par période de validation
    @Query("SELECT t FROM Transaction t WHERE t.dateValidation BETWEEN :dateDebut AND :dateFin")
    List<Transaction> findByDateValidationBetween(
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin
    );

    // Transactions en attente
    @Query("SELECT t FROM Transaction t WHERE t.etatTransaction = 'EN_ATTENTE' ORDER BY t.dateDemande ASC")
    List<Transaction> findTransactionsEnAttente();

    @Query("SELECT t FROM Transaction t WHERE t.etatTransaction = 'EN_ATTENTE' ORDER BY t.dateDemande ASC")
    Page<Transaction> findTransactionsEnAttente(Pageable pageable);

    // Transactions impliquant une agence (source ou destination)
    @Query("SELECT t FROM Transaction t WHERE t.agenceSource.id = :agenceId OR t.agenceDestination.id = :agenceId")
    List<Transaction> findByAgenceImpliquee(@Param("agenceId") Long agenceId);

    @Query("SELECT t FROM Transaction t WHERE t.agenceSource.id = :agenceId OR t.agenceDestination.id = :agenceId")
    Page<Transaction> findByAgenceImpliquee(@Param("agenceId") Long agenceId, Pageable pageable);

    // Statistiques - Nombre de transactions par état
    @Query("SELECT t.etatTransaction, COUNT(t) FROM Transaction t GROUP BY t.etatTransaction")
    List<Object[]> countTransactionsByEtat();

    // Statistiques - Nombre de transactions par type
    @Query("SELECT t.typeTransaction, COUNT(t) FROM Transaction t GROUP BY t.typeTransaction")
    List<Object[]> countTransactionsByType();

    // Recherche avec critères multiples
    @Query("SELECT t FROM Transaction t WHERE " +
            "(:immobilisationId IS NULL OR t.immobilisation.id = :immobilisationId) AND " +
            "(:demandeurId IS NULL OR t.demandeur.id = :demandeurId) AND " +
            "(:validateurId IS NULL OR t.validateur.id = :validateurId) AND " +
            "(:etatTransaction IS NULL OR t.etatTransaction = :etatTransaction) AND " +
            "(:typeTransaction IS NULL OR t.typeTransaction = :typeTransaction) AND " +
            "(:agenceSourceId IS NULL OR t.agenceSource.id = :agenceSourceId) AND " +
            "(:agenceDestinationId IS NULL OR t.agenceDestination.id = :agenceDestinationId) AND " +
            "(:dateDebut IS NULL OR t.dateDemande >= :dateDebut) AND " +
            "(:dateFin IS NULL OR t.dateDemande <= :dateFin)")
    Page<Transaction> findWithCriteria(
            @Param("immobilisationId") Long immobilisationId,
            @Param("demandeurId") Long demandeurId,
            @Param("validateurId") Long validateurId,
            @Param("etatTransaction") EtatTransaction etatTransaction,
            @Param("typeTransaction") TypeTransaction typeTransaction,
            @Param("agenceSourceId") Long agenceSourceId,
            @Param("agenceDestinationId") Long agenceDestinationId,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin,
            Pageable pageable
    );

    // Transactions récentes d'une immobilisation
    @Query("SELECT t FROM Transaction t WHERE t.immobilisation.id = :immobilisationId ORDER BY t.dateDemande DESC")
    List<Transaction> findRecentTransactionsByImmobilisation(@Param("immobilisationId") Long immobilisationId);

    // Vérifier si une immobilisation a des transactions en attente
    @Query("SELECT COUNT(t) > 0 FROM Transaction t WHERE t.immobilisation.id = :immobilisationId AND t.etatTransaction = 'EN_ATTENTE'")
    boolean hasTransactionsEnAttenteForImmobilisation(@Param("immobilisationId") Long immobilisationId);
}