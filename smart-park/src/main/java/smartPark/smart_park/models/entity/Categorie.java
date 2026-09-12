package smartPark.smart_park.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String nom;
    /**
     * Code court de la catégorie (INFO, MOB, VEH...), utilisé comme segment
     * dans les codes d'immobilisation. Nullable en base pour permettre la
     * montée de version sur un schéma existant ; DataInitializer réalise le
     * rattrapage et le DTO de requête le rend obligatoire à la saisie.
     */
    @Column(unique = true, length = 10)
    private String code;
    @Column(length = 500)
    private String description;
    /**
     * Durée d'amortissement par défaut des biens de cette catégorie, en mois
     * (informatique ~36, véhicule ~60, mobilier ~120).
     */
    @Column(name = "duree_amortissement_mois")
    private Integer dureeAmortissementMois;
    // @Builder.Default : sans lui, Categorie.builder() ignore ces valeurs par
    // défaut et produit un actif null, qui viole la contrainte NOT NULL.
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "methode_amortissement", length = 20)
    private MethodeAmortissement methodeAmortissement = MethodeAmortissement.LINEAIRE;
    @Builder.Default
    @Column(nullable = false)
    private Boolean actif = true;
    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    //relation avec la classe Immobilisations
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Immobilisation> immobilisations;
}
