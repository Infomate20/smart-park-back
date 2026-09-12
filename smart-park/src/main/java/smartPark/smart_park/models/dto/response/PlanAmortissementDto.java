package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanAmortissementDto {

    private Long immobilisationId;
    private String codeImmobilisation;
    private String designation;
    private String categorieNom;

    /** Faux si le plan ne peut pas être établi ; {@link #motifNonAmortissable} l'explique alors. */
    private boolean amortissable;
    private String motifNonAmortissable;

    private MethodeAmortissement methodeAmortissement;
    private String methodeAmortissementLibelle;
    private Integer dureeAmortissementMois;
    /** Coefficient fiscal appliqué en dégressif ; null en linéaire. */
    private BigDecimal coefficientDegressif;

    private BigDecimal baseAmortissable;
    private LocalDate dateDebutAmortissement;
    private LocalDate dateFinAmortissement;

    /** Situation à la date du calcul. */
    private BigDecimal cumulAmortissements;
    private BigDecimal valeurNetteComptable;

    /**
     * Tableau exercice par exercice. Renseigné sur le plan d'un bien ;
     * omis (null) dans la synthèse paginée du parc, pour ne pas alourdir la page.
     */
    private List<LigneAmortissementDto> lignes;
}
