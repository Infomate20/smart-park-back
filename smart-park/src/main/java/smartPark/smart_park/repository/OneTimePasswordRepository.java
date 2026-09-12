package smartPark.smart_park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartPark.smart_park.models.entity.OneTimePassword;
import smartPark.smart_park.models.entity.Utilisateur;
import java.util.Optional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, Long> {
    Optional<OneTimePassword> findByUtilisateurAndCode(Utilisateur utilisateur, String code);
}