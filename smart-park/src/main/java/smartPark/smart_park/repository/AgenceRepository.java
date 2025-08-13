package smartPark.smart_park.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartPark.smart_park.models.entity.Agence;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {

    Optional<Agence> findByCode(String code);

    Optional<Agence> findByNom(String nom);

    Optional<Agence> findByEmail(String email);

    List<Agence> findByActifTrue();

    List<Agence> findByVille(String ville);

    List<Agence> findByPays(String pays);

    @Query("SELECT a FROM Agence a WHERE " +
            "(:searchTerm IS NULL OR :searchTerm = '' OR " +
            "    LOWER(a.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(a.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(a.ville) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(a.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND " +
            "(:actif IS NULL OR a.actif = :actif)")
    Page<Agence> findWithFilters(
            @Param("searchTerm") String searchTerm,
            @Param("actif") Boolean actif,
            Pageable pageable
    );

    Page<Agence> findByActifTrue(Pageable pageable);

    @Query("SELECT a FROM Agence a WHERE a.actif = true AND " +
            "(LOWER(a.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.ville) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.adresse) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(a.nomResponsable) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Agence> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT a FROM Agence a WHERE a.ville = :ville AND a.actif = true")
    Page<Agence> findByVilleAndActifTrue(@Param("ville") String ville, Pageable pageable);

    @Query("SELECT a FROM Agence a WHERE a.pays = :pays AND a.actif = true")
    Page<Agence> findByPaysAndActifTrue(@Param("pays") String pays, Pageable pageable);

    boolean existsByCodeAndIdNot(String code, Long id);

    boolean existsByNomAndIdNot(String nom, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByCode(String code);

    boolean existsByNom(String nom);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(i) FROM Immobilisation i WHERE i.agence.id = :agenceId AND i.actif = true")
    Long countImmobilisationsActivesByAgenceId(@Param("agenceId") Long agenceId);

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.agence.id = :agenceId AND u.actif = true")
    Long countUtilisateursActifsByAgenceId(@Param("agenceId") Long agenceId);

    @Query("SELECT DISTINCT a.ville FROM Agence a WHERE a.actif = true ORDER BY a.ville")
    List<String> findDistinctVillesActives();

    @Query("SELECT DISTINCT a.pays FROM Agence a WHERE a.actif = true ORDER BY a.pays")
    List<String> findDistinctPaysActifs();
}

