package smartPark.smart_park.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.Immobilisation;

import java.util.List;
import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    Optional<Categorie> findByNom(String nom);
    List<Categorie> findByActifTrue();

    Page<Categorie> findByActifTrue(Pageable pageable);

    @Query("SELECT c FROM Categorie c WHERE c.actif = true AND " +
            "(LOWER(c.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Categorie> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    boolean existsByNomAndIdNot(String nom, Long id);

    @Query("SELECT c FROM Categorie c WHERE " +
            // Condition 1 : Filtre sur le terme de recherche (optionnel)
            "(:searchTerm IS NULL OR :searchTerm = '' OR " +
            "    LOWER(c.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')))" +
            // Condition 2 : Filtre sur le statut (optionnel)
            "AND (:actif IS NULL OR c.actif = :actif)")
    Page<Categorie> findWithFilter(
            @Param("searchTerm") String searchTerm,
            @Param("actif") Boolean actif,
            Pageable pageable
    );
}

