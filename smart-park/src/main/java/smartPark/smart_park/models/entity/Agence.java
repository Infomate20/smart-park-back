package smartPark.smart_park.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "agence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Column(nullable = false, unique = true, length = 200)
    private String nom;

    @Column(length = 500)
    private String description;

    @Column(length = 200)
    private String adresse;

    @Column(length = 100)
    private String ville;

    @Column(name = "code_postal", length = 10)
    private String codePostal;

    @Column(length = 100)
    private String pays;

    @Column(length = 20)
    private String telephone;

    @Column(length = 20)
    private String fax;

    @Column(length = 100)
    private String email;

    @Column(name = "nom_responsable", length = 100)
    private String nomResponsable;

    @Column(name = "telephone_responsable", length = 20)
    private String telephoneResponsable;

    @Column(name = "email_responsable", length = 100)
    private String emailResponsable;

    @Column(nullable = false)
    private Boolean actif = true;

    @CreationTimestamp
    @Column(name = "date_creation", nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    // Relations
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Immobilisation> immobilisations;

    @OneToMany(mappedBy = "agenceSource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactionsSource;

    @OneToMany(mappedBy = "agenceDestination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactionsDestination;

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Utilisateur> utilisateurs;


}