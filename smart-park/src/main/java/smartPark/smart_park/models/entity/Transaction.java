package smartPark.smart_park.models.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import smartPark.smart_park.models.entity.enums.EtatTransaction;
import smartPark.smart_park.models.entity.enums.TypeTransaction;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le type de transaction est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false)
    private TypeTransaction typeTransaction;

    @NotNull(message = "L'état de la transaction est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "etat_transaction", nullable = false)
    private EtatTransaction etatTransaction;

    @NotNull(message = "La date de demande est obligatoire")
    @Column(name = "date_demande", nullable = false)
    private LocalDateTime dateDemande;

    @Column(name = "date_validation")
    private LocalDateTime dateValidation;

    @Size(max = 1000, message = "Le motif ne peut pas dépasser 1000 caractères")
    @Column(length = 1000)
    private String motif;

    @Size(max = 500, message = "Les observations ne peuvent pas dépasser 500 caractères")
    @Column(length = 500)
    private String observations;

    @Size(max = 500, message = "Le motif de rejet ne peut pas dépasser 500 caractères")
    @Column(name = "motif_rejet", length = 500)
    private String motifRejet;

    // Relations
    @NotNull(message = "L'immobilisation est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "immobilisation_id", nullable = false)
    private Immobilisation immobilisation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_source_id")
    private Agence agenceSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_destination_id")
    private Agence agenceDestination;

    @NotNull(message = "L'utilisateur demandeur est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandeur_id", nullable = false)
    private Utilisateur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validateur_id")
    private Utilisateur validateur;

    // Audit
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Méthodes utilitaires
    public boolean estEnAttente() {
        return this.etatTransaction == EtatTransaction.EN_ATTENTE;
    }

    public boolean estValidee() {
        return this.etatTransaction == EtatTransaction.VALIDEE;
    }

    public boolean estRejetee() {
        return this.etatTransaction == EtatTransaction.REJETEE;
    }

    public boolean peutEtreValidee() {
        return this.etatTransaction == EtatTransaction.EN_ATTENTE;
    }

    public boolean peutEtreRejetee() {
        return this.etatTransaction == EtatTransaction.EN_ATTENTE;
    }
}