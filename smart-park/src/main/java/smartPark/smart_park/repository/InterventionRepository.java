package smartPark.smart_park.repository;


import smartPark.smart_park.models.entity.Intervention;
import smartPark.smart_park.models.entity.enums.EtatIntervention;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, Long> {

    // Recherche par immobilisation
    List<Intervention> findByImmobilisationId(Long immobilisationId);

    Page<Intervention> findByImmobilisationId(Long immobilisationId, Pageable pageable);

    // Recherche par technicien
    List<Intervention> findByTechnicienId(Long technicienId);

    Page<Intervention> findByTechnicienId(Long technicienId, Pageable pageable);

    // Recherche par type d'intervention
    List<Intervention> findByTypeIntervention(TypeIntervention typeIntervention);

    Page<Intervention> findByTypeIntervention(TypeIntervention typeIntervention, Pageable pageable);

    // Recherche par période
    @Query("SELECT i FROM Intervention i WHERE i.dateIntervention BETWEEN :dateDebut AND :dateFin")
    List<Intervention> findByDateInterventionBetween(
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin
    );

    // Recherche par agence de l'immobilisation
    @Query("SELECT i FROM Intervention i WHERE i.immobilisation.agence.id = :agenceId")
    List<Intervention> findByAgenceId(@Param("agenceId") Long agenceId);

    @Query("SELECT i FROM Intervention i WHERE i.immobilisation.agence.id = :agenceId")
    Page<Intervention> findByAgenceId(@Param("agenceId") Long agenceId, Pageable pageable);

    // Statistiques - Nombre d'interventions par type
    @Query("SELECT i.typeIntervention, COUNT(i) FROM Intervention i GROUP BY i.typeIntervention")
    List<Object[]> countInterventionsByType();

    // Recherche avec critères multiples
    @Query("SELECT i FROM Intervention i WHERE " +
            "(:immobilisationId IS NULL OR i.immobilisation.id = :immobilisationId) AND " +
            "(:technicienId IS NULL OR i.technicien.id = :technicienId) AND " +
            "(:typeIntervention IS NULL OR i.typeIntervention = :typeIntervention) AND " +
            "(:dateDebut IS NULL OR i.dateIntervention >= :dateDebut) AND " +
            "(:dateFin IS NULL OR i.dateIntervention <= :dateFin)")
    Page<Intervention> findWithCriteria(
            @Param("immobilisationId") Long immobilisationId,
            @Param("technicienId") Long technicienId,
            @Param("typeIntervention") TypeIntervention typeIntervention,
            @Param("dateDebut") LocalDateTime dateDebut,
            @Param("dateFin") LocalDateTime dateFin,
            Pageable pageable
    );

    // Dernières interventions d'une immobilisation
    @Query("SELECT i FROM Intervention i WHERE i.immobilisation.id = :immobilisationId ORDER BY i.dateIntervention DESC")
    List<Intervention> findLastInterventionsByImmobilisation(@Param("immobilisationId") Long immobilisationId);

//    List<Intervention> findWithFilter(
//            @Param("serachterm") String searchTerm,
//            @Param("etatIntervention")EtatIntervention etatIntervention
//            );
}