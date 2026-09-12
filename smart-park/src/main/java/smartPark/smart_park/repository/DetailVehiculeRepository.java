package smartPark.smart_park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartPark.smart_park.models.entity.DetailVehicule;

import java.util.Optional;

public interface DetailVehiculeRepository extends JpaRepository<DetailVehicule, Long> {

    Optional<DetailVehicule> findByImmobilisationId(Long immobilisationId);

    Optional<DetailVehicule> findByImmatriculation(String immatriculation);

    boolean existsByImmatriculationAndIdNot(String immatriculation, Long id);

    void deleteByImmobilisationId(Long immobilisationId);
}
