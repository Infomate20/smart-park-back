package smartPark.smart_park.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartPark.smart_park.models.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Pour l'instant, les méthodes de JpaRepository suffisent.
    // On pourra ajouter des requêtes personnalisées plus tard.
}