package smartPark.smart_park.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.models.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByNomUtilisateur(String nomUtilisateur);

    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByMatricule(String matricule);

    List<Utilisateur> findByActifTrue();

    @Query("SELECT u FROM Utilisateur u WHERE " +
            // --- Bloc 1 : Condition sur le terme de recherche (optionnelle) ---
            "(:searchTerm IS NULL OR :searchTerm = '' OR " +
            "    LOWER(u.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(u.nomUtilisateur) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "    LOWER(u.matricule) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND " +
            // --- Bloc 2 : Condition sur le statut 'actif' (optionnelle) ---
            "(:actif IS NULL OR u.actif = :actif)")
    Page<Utilisateur> findWithFilter(
            @Param("searchTerm") String searchTerm,
            @Param("actif") Boolean actif,
            Pageable pageable
    );
    List<Utilisateur> findByRole(Role role);

    List<Utilisateur> findByAgenceId(Long agenceId);

    List<Utilisateur> findByAgenceIsNull();

    List<Utilisateur> findByCompteVerrouilleFalseAndActifTrue();

    List<Utilisateur> findByPremiereConnexionTrue();

    Page<Utilisateur> findByActifTrue(Pageable pageable);

    @Query("SELECT u FROM Utilisateur u WHERE u.actif = true AND " +
            "(LOWER(u.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.nomUtilisateur) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.matricule) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.poste) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Utilisateur> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT u FROM Utilisateur u WHERE u.role = :role AND u.actif = true")
    Page<Utilisateur> findByRoleAndActifTrue(@Param("role") Role role, Pageable pageable);

    @Query("SELECT u FROM Utilisateur u WHERE u.agence.id = :agenceId AND u.actif = true")
    Page<Utilisateur> findByAgenceIdAndActifTrue(@Param("agenceId") Long agenceId, Pageable pageable);

    @Query("SELECT u FROM Utilisateur u WHERE u.agence IS NULL AND u.actif = true")
    Page<Utilisateur> findByAgenceIsNullAndActifTrue(Pageable pageable);

    @Query("SELECT u FROM Utilisateur u WHERE u.compteVerrouille = true AND u.actif = true")
    Page<Utilisateur> findByCompteVerrouilleTrue(Pageable pageable);

    boolean existsByNomUtilisateurAndIdNot(String nomUtilisateur, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByMatriculeAndIdNot(String matricule, Long id);

    boolean existsByNomUtilisateur(String nomUtilisateur);

    boolean existsByEmail(String email);

    boolean existsByMatricule(String matricule);

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.role = :role AND u.actif = true")
    Long countByRoleAndActifTrue(@Param("role") Role role);

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.agence.id = :agenceId AND u.actif = true")
    Long countByAgenceIdAndActifTrue(@Param("agenceId") Long agenceId);

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.compteVerrouille = true AND u.actif = true")
    Long countByCompteVerrouilleTrue();

    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE u.premiereConnexion = true AND u.actif = true")
    Long countByPremiereConnexionTrue();

    @Modifying
    @Query("UPDATE Utilisateur u SET u.derniereConnexion = :dateConnexion WHERE u.id = :id")
    void updateDerniereConnexion(@Param("id") Long id, @Param("dateConnexion") LocalDateTime dateConnexion);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.tentativesConnexionEchouees = :tentatives WHERE u.id = :id")
    void updateTentativesConnexionEchouees(@Param("id") Long id, @Param("tentatives") Integer tentatives);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.compteVerrouille = :verrouille WHERE u.id = :id")
    void updateCompteVerrouille(@Param("id") Long id, @Param("verrouille") Boolean verrouille);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.premiereConnexion = false WHERE u.id = :id")
    void marquerPremiereConnexionTerminee(@Param("id") Long id);

    @Query("SELECT u FROM Utilisateur u WHERE u.derniereConnexion < :dateLimit AND u.actif = true")
    List<Utilisateur> findUtilisateursInactifsSince(@Param("dateLimit") LocalDateTime dateLimit);
}