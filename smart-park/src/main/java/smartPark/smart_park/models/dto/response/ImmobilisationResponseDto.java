package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImmobilisationResponseDto {

    private Long id;
    private String codeImmobilisation;
    private String numeroSerie;
    private String designation;
    private String marque;
    private String modele;
    private LocalDate dateAcquisition;
    private BigDecimal prixAcquisition;
    private BigDecimal valeurActuelle;
    private EtatImmobilisation etat;
    private String etatLibelle;
    private LocalDate dateMiseEnService;
    private Integer dureeGarantieMois;
    private String observations;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    // Informations de la catégorie
    private Long categorieId;
    private String categorieNom;
    // Informations de l'agence (optionnel)
    private Long agenceId;
    private String agenceNom;
    // Statistiques
    private Long nombreInterventions;
    private Long nombreTransactions;
    // Informations calculées
    private Boolean sousGarantie;
    private Integer ageEnMois;
}
