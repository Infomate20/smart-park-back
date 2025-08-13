package smartPark.smart_park.models.entity;


import smartPark.smart_park.models.entity.enums.EtatIntervention;
import smartPark.smart_park.models.entity.enums.Orientation;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "interventions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intervention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La description de l'intervention est obligatoire")
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    @Column(nullable = false, length = 1000)
    private String description;

    @NotNull(message = "La date d'intervention est obligatoire")
    @Column(name = "date_intervention", nullable = false)
    private LocalDateTime dateIntervention;

    @Column(name = "cout_intervention", precision = 10, scale = 2)
    private BigDecimal coutIntervention;

    @Size(max = 500, message = "Les observations ne peuvent pas dépasser 500 caractères")
    @Column(length = 500)
    private String observations;

    @NotNull(message = "Le type d'intervention est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(name = "type_intervention", nullable = false)
    private TypeIntervention typeIntervention;

    @Enumerated(EnumType.STRING)
    @Column(name = "orientation")
    private Orientation orientation;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_intervention", nullable = false)
    private EtatIntervention etatIntervention;

    @Column(name="date_cloture")
    private LocalDateTime dateCloture;
    // Relations
    @NotNull(message = "L'immobilisation est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "immobilisation_id", nullable = false)
    private Immobilisation immobilisation;

    @NotNull(message = "Le technicien est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technicien_id", nullable = false)
    private Utilisateur technicien;

    // Audit
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
