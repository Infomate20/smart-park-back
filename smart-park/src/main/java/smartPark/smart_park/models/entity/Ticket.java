package smartPark.smart_park.models.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import smartPark.smart_park.models.entity.enums.EtatTicket;
import smartPark.smart_park.models.entity.Immobilisation;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "immobilisation_id", nullable = false)
    private Immobilisation immobilisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandeur_id", nullable = false)
    private Utilisateur demandeur;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    @Column(nullable = false, length = 2000) // Augmentation de la taille pour les descriptions longues
    private String descriptionProbleme;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatTicket etat;

    // --- Champs pour le workflow de validation ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validateur_id")
    private Utilisateur validateur;

    private LocalDateTime dateValidation;

    @Column(length = 1000)
    private String motifRejet;

    @OneToOne
    @JoinColumn(name = "intervention_id")
    private Intervention interventionLiee;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        etat = EtatTicket.NOUVEAU; // Un nouveau ticket est toujours à l'état NOUVEAU
    }
}