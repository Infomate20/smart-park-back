package smartPark.smart_park.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "immobilisations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Immobilisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_immobilisation", nullable = false, unique = true, length = 50)
    private String codeImmobilisation;

    @Column(name = "numero_serie", nullable = false, length = 100)
    private String numeroSerie;
    @Column(nullable = false, length = 200)
    private String designation;
    @Column(length = 100)
    private String marque;
    @Column(length = 100)
    private String modele;
    @Column(name = "date_acquisition")
    private LocalDate dateAcquisition;
    @Column(name = "prix_acquisition", precision = 15, scale = 2)
    private BigDecimal prixAcquisition;
    @Column(name = "valeur_actuelle", precision = 15, scale = 2)
    private BigDecimal valeurActuelle;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EtatImmobilisation etat = EtatImmobilisation.EN_SERVICE;
    @Column(name = "date_mise_en_service")
    private LocalDate dateMiseEnService;
    @Column(name = "duree_garantie_mois")
    private Integer dureeGarantieMois;
    @Column(length = 1000)
    private String observations;
    @Column(nullable = false)
    private Boolean actif = true;
    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    // Relations
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agence_id")
    private Agence agence;
    @OneToMany(mappedBy = "immobilisation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Intervention> interventions;
    @OneToMany(mappedBy = "immobilisation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

}
