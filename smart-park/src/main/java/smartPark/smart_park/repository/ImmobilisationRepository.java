package smartPark.smart_park.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImmobilisationRepository extends JpaRepository<Immobilisation, Long> {

    Optional<Immobilisation> findByCodeImmobilisation(String codeImmobilisation);

    Optional<Immobilisation> findByNumeroSerie(String numeroSerie);

    List<Immobilisation> findByActifTrue();

    List<Immobilisation> findByEtat(EtatImmobilisation etat);

    List<Immobilisation> findByCategorieId(Long categorieId);

    List<Immobilisation> findByAgenceId(Long agenceId);

    List<Immobilisation> findByAgenceIsNull();

    Page<Immobilisation> findByActifTrue(Pageable pageable);

    @Query("SELECT i FROM Immobilisation i WHERE i.actif = true AND " +
            "(LOWER(i.designation) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.codeImmobilisation) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.numeroSerie) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.marque) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(i.modele) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Immobilisation> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT i FROM Immobilisation i WHERE i.categorie.id = :categorieId AND i.actif = true")
    Page<Immobilisation> findByCategorieIdAndActifTrue(@Param("categorieId") Long categorieId, Pageable pageable);

    @Query("SELECT i FROM Immobilisation i WHERE i.agence.id = :agenceId AND i.actif = true")
    Page<Immobilisation> findByAgenceIdAndActifTrue(@Param("agenceId") Long agenceId, Pageable pageable);

    @Query("SELECT i FROM Immobilisation i WHERE i.agence IS NULL AND i.actif = true")
    Page<Immobilisation> findByAgenceIsNullAndActifTrue(Pageable pageable);

    @Query("SELECT i FROM Immobilisation i WHERE i.etat = :etat AND i.actif = true")
    Page<Immobilisation> findByEtatAndActifTrue(@Param("etat") EtatImmobilisation etat, Pageable pageable);

    @Query("SELECT i FROM Immobilisation i WHERE i.dateAcquisition BETWEEN :dateDebut AND :dateFin AND i.actif = true")
    List<Immobilisation> findByDateAcquisitionBetween(@Param("dateDebut") LocalDate dateDebut, @Param("dateFin") LocalDate dateFin);

    @Query("SELECT i FROM Immobilisation i WHERE i.prixAcquisition BETWEEN :prixMin AND :prixMax AND i.actif = true")
    Page<Immobilisation> findByPrixAcquisitionBetween(@Param("prixMin") BigDecimal prixMin, @Param("prixMax") BigDecimal prixMax, Pageable pageable);

    boolean existsByCodeImmobilisationAndIdNot(String codeImmobilisation, Long id);

    boolean existsByNumeroSerieAndIdNot(String numeroSerie, Long id);

    boolean existsByCodeImmobilisation(String codeImmobilisation);

    boolean existsByNumeroSerie(String numeroSerie);

    @Query("SELECT COUNT(i) FROM Immobilisation i WHERE i.categorie.id = :categorieId AND i.actif = true")
    Long countByCategorieIdAndActifTrue(@Param("categorieId") Long categorieId);

    @Query("SELECT COUNT(i) FROM Immobilisation i WHERE i.agence.id = :agenceId AND i.actif = true")
    Long countByAgenceIdAndActifTrue(@Param("agenceId") Long agenceId);

    @Query("SELECT COUNT(i) FROM Immobilisation i WHERE i.etat = :etat AND i.actif = true")
    Long countByEtatAndActifTrue(@Param("etat") EtatImmobilisation etat);


    @Query("SELECT i FROM Immobilisation i WHERE " +
            // Condition 1 : Filtre sur le terme de recherche (optionnel)
            "(:searchTerm IS NULL OR :searchTerm = '' OR " +
            "    LOWER(i.designation) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(i.codeImmobilisation) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(i.numeroSerie) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(i.marque) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(i.modele) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND " +
            // Condition 2 : Filtre sur le statut (optionnel)
            "(:actif IS NULL OR i.actif = :actif)")
    Page<Immobilisation> findWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("actif") Boolean actif,
            Pageable pageable
    );
}