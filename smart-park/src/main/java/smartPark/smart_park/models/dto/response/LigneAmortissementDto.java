package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/** Une ligne du tableau d'amortissement, pour un exercice (année civile). */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LigneAmortissementDto {

    private int exercice;
    /** Valeur nette comptable à l'ouverture de l'exercice. */
    private BigDecimal valeurDebut;
    /** Taux effectivement appliqué sur l'exercice, en pourcentage. */
    private BigDecimal taux;
    private BigDecimal dotation;
    private BigDecimal cumulAmortissements;
    /** Valeur nette comptable à la clôture de l'exercice. */
    private BigDecimal valeurNetteComptable;
}
