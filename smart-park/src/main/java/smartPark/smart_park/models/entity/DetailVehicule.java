package smartPark.smart_park.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import smartPark.smart_park.models.entity.enums.TypeCarburant;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Attributs propres aux véhicules, en satellite de {@link Immobilisation}.
 *
 * <p>Table séparée plutôt que colonnes nullables sur {@code immobilisations} :
 * les biens non véhicules (mobilier, matériel informatique) ne portent pas ces
 * colonnes, et ajouter un nouveau type de bien n'impose pas de toucher à la
 * table principale.
 *
 * <p>Le numéro de châssis (VIN) n'est pas repris ici : c'est le numéro de série
 * constructeur du véhicule, il se saisit dans {@code Immobilisation.numeroSerie}.
 *
 * <p>Côté propriétaire de la relation : {@code Immobilisation} ne référence pas
 * ce satellite, afin que le chargement d'un bien reste inchangé partout ailleurs.
 * L'accès se fait par {@code DetailVehiculeRepository.findByImmobilisationId}.
 */
@Entity
@Table(name = "detail_vehicules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailVehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "immobilisation_id", nullable = false, unique = true)
    private Immobilisation immobilisation;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    private Integer kilometrage;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_carburant", length = 20)
    private TypeCarburant typeCarburant;

    @Column(name = "puissance_fiscale")
    private Integer puissanceFiscale;

    @Column(name = "nombre_places")
    private Integer nombrePlaces;

    // Contrôle technique
    @Column(name = "date_dernier_controle_technique")
    private LocalDate dateDernierControleTechnique;
    @Column(name = "date_prochain_controle_technique")
    private LocalDate dateProchainControleTechnique;

    // Assurance
    @Column(name = "compagnie_assurance", length = 100)
    private String compagnieAssurance;
    @Column(name = "numero_police_assurance", length = 50)
    private String numeroPoliceAssurance;
    @Column(name = "date_expiration_assurance")
    private LocalDate dateExpirationAssurance;

    @Column(length = 1000)
    private String observations;

    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}
