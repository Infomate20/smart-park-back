package smartPark.smart_park.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.dto.response.LigneAmortissementDto;
import smartPark.smart_park.models.dto.response.PlanAmortissementDto;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;
import smartPark.smart_park.repository.ImmobilisationRepository;
import smartPark.smart_park.services.AmortissementService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Calcul des plans d'amortissement.
 *
 * <p>La durée et la méthode proviennent de la {@link Categorie} du bien : c'est
 * l'axe métier pertinent (informatique ~36 mois, véhicule ~60, mobilier ~120).
 *
 * <p>Rien n'est stocké : la VNC est recalculée à la demande. Le champ
 * {@code Immobilisation.valeurActuelle} reste une saisie manuelle libre (valeur
 * de marché, réévaluation) et n'est volontairement pas écrasé par ce module.
 *
 * <p>Conventions retenues :
 * <ul>
 *   <li>l'exercice est l'année civile ;</li>
 *   <li>en linéaire, le prorata de la première année est calculé en mois à
 *       partir du mois de mise en service ;</li>
 *   <li>en dégressif, le coefficient fiscal dépend de la durée (1,25 de 3 à 4
 *       ans, 1,75 de 5 à 6 ans, 2,25 au-delà) et le plan bascule en linéaire dès
 *       que le taux linéaire sur la durée restante devient plus avantageux ;</li>
 *   <li>une durée inférieure à 3 ans n'ouvre pas droit au dégressif : le plan
 *       est alors calculé en linéaire ;</li>
 *   <li>les arrondis sont au centime, le dernier exercice absorbant le résidu
 *       pour que le cumul égale exactement la base amortissable.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AmortissementServiceImpl implements AmortissementService {

    private static final int ECHELLE_MONETAIRE = 2;
    private static final int MAX_EXERCICES = 100;

    private final ImmobilisationRepository immobilisationRepository;

    @Override
    public PlanAmortissementDto calculerPlan(Long immobilisationId) {
        log.info("Calcul du plan d'amortissement de l'immobilisation ID: {}", immobilisationId);
        return construirePlan(chargerImmobilisation(immobilisationId), LocalDate.now(), true);
    }

    @Override
    public PlanAmortissementDto calculerSituation(Long immobilisationId, LocalDate dateSituation) {
        LocalDate date = dateSituation != null ? dateSituation : LocalDate.now();
        log.info("Calcul de la situation d'amortissement de l'immobilisation ID: {} au {}", immobilisationId, date);
        return construirePlan(chargerImmobilisation(immobilisationId), date, false);
    }

    @Override
    public Page<PlanAmortissementDto> obtenirSyntheseParc(LocalDate dateSituation, Pageable pageable) {
        LocalDate date = dateSituation != null ? dateSituation : LocalDate.now();
        log.info("Synthèse d'amortissement du parc au {} (page {}, taille {})",
                date, pageable.getPageNumber(), pageable.getPageSize());

        return immobilisationRepository.findAll(pageable)
                .map(immobilisation -> construirePlan(immobilisation, date, false));
    }

    private Immobilisation chargerImmobilisation(Long id) {
        return immobilisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + id));
    }

    private PlanAmortissementDto construirePlan(Immobilisation immobilisation,
                                                LocalDate dateSituation,
                                                boolean avecLignes) {
        PlanAmortissementDto plan = new PlanAmortissementDto();
        plan.setImmobilisationId(immobilisation.getId());
        plan.setCodeImmobilisation(immobilisation.getCodeImmobilisation());
        plan.setDesignation(immobilisation.getDesignation());

        Categorie categorie = immobilisation.getCategorie();
        if (categorie != null) {
            plan.setCategorieNom(categorie.getNom());
            plan.setMethodeAmortissement(categorie.getMethodeAmortissement());
            plan.setMethodeAmortissementLibelle(categorie.getMethodeAmortissement() != null
                    ? categorie.getMethodeAmortissement().getLibelle() : null);
            plan.setDureeAmortissementMois(categorie.getDureeAmortissementMois());
        }

        BigDecimal base = immobilisation.getPrixAcquisition();
        LocalDate debut = immobilisation.getDateMiseEnService() != null
                ? immobilisation.getDateMiseEnService()
                : immobilisation.getDateAcquisition();
        plan.setBaseAmortissable(base);
        plan.setDateDebutAmortissement(debut);

        String blocage = motifNonAmortissable(categorie, base, debut);
        if (blocage != null) {
            plan.setAmortissable(false);
            plan.setMotifNonAmortissable(blocage);
            plan.setCumulAmortissements(BigDecimal.ZERO.setScale(ECHELLE_MONETAIRE, RoundingMode.HALF_UP));
            plan.setValeurNetteComptable(base != null ? arrondir(base) : null);
            return plan;
        }

        plan.setAmortissable(true);
        int dureeMois = categorie.getDureeAmortissementMois();

        List<LigneAmortissementDto> lignes;
        if (categorie.getMethodeAmortissement() == MethodeAmortissement.DEGRESSIF
                && coefficientDegressif(dureeMois) != null) {
            plan.setCoefficientDegressif(coefficientDegressif(dureeMois));
            lignes = calculerDegressif(base, debut, dureeMois);
        } else {
            lignes = calculerLineaire(base, debut, dureeMois);
        }

        plan.setDateFinAmortissement(debut.plusMonths(dureeMois).minusDays(1));
        appliquerSituation(plan, lignes, base, dateSituation);

        if (avecLignes) {
            plan.setLignes(lignes);
        }
        return plan;
    }

    private String motifNonAmortissable(Categorie categorie, BigDecimal base, LocalDate debut) {
        if (categorie == null) {
            return "Le bien n'est rattaché à aucune catégorie";
        }
        if (categorie.getMethodeAmortissement() == MethodeAmortissement.NON_AMORTISSABLE) {
            return "La catégorie " + categorie.getNom() + " est déclarée non amortissable";
        }
        if (categorie.getDureeAmortissementMois() == null || categorie.getDureeAmortissementMois() <= 0) {
            return "Aucune durée d'amortissement n'est définie sur la catégorie " + categorie.getNom();
        }
        if (base == null || base.signum() <= 0) {
            return "Le prix d'acquisition du bien n'est pas renseigné";
        }
        if (debut == null) {
            return "Ni la date de mise en service ni la date d'acquisition ne sont renseignées";
        }
        return null;
    }

    /**
     * Linéaire : la dotation est proportionnelle au nombre de mois du plan
     * tombant dans l'exercice, ce qui donne naturellement le prorata temporis
     * de la première et de la dernière année.
     */
    private List<LigneAmortissementDto> calculerLineaire(BigDecimal base, LocalDate debut, int dureeMois) {
        BigDecimal dotationMensuelle = base.divide(BigDecimal.valueOf(dureeMois), 10, RoundingMode.HALF_UP);

        List<LigneAmortissementDto> lignes = new ArrayList<>();
        int exerciceCourant = debut.getYear();
        int moisDansExercice = 0;

        BigDecimal cumul = BigDecimal.ZERO;
        BigDecimal valeurDebut = base;

        for (int mois = 0; mois < dureeMois; mois++) {
            int annee = debut.plusMonths(mois).getYear();
            if (annee != exerciceCourant) {
                BigDecimal dotation = arrondir(dotationMensuelle.multiply(BigDecimal.valueOf(moisDansExercice)));
                cumul = cumul.add(dotation);
                lignes.add(ligne(exerciceCourant, valeurDebut, tauxEffectif(dotation, valeurDebut), dotation,
                        cumul, base.subtract(cumul)));
                valeurDebut = base.subtract(cumul);

                exerciceCourant = annee;
                moisDansExercice = 0;
            }
            moisDansExercice++;
        }

        BigDecimal dotation = arrondir(dotationMensuelle.multiply(BigDecimal.valueOf(moisDansExercice)));
        cumul = cumul.add(dotation);
        lignes.add(ligne(exerciceCourant, valeurDebut, tauxEffectif(dotation, valeurDebut), dotation,
                cumul, base.subtract(cumul)));

        return ajusterDernierExercice(lignes, base);
    }

    /**
     * Dégressif : taux constant appliqué à la valeur résiduelle, avec bascule en
     * linéaire dès que le taux linéaire sur la durée restante devient supérieur.
     */
    private List<LigneAmortissementDto> calculerDegressif(BigDecimal base, LocalDate debut, int dureeMois) {
        double dureeAnnees = dureeMois / 12.0;
        double tauxDegressif = (1.0 / dureeAnnees) * coefficientDegressif(dureeMois).doubleValue();

        // Convention fiscale : la première année court à compter du 1er du mois
        // de mise en service, en mois entiers.
        double prorataPremiereAnnee = (12 - debut.getMonthValue() + 1) / 12.0;

        List<LigneAmortissementDto> lignes = new ArrayList<>();
        BigDecimal valeurResiduelle = base;
        BigDecimal cumul = BigDecimal.ZERO;
        double anneesRestantes = dureeAnnees;
        int exercice = debut.getYear();

        while (anneesRestantes > 0.0001 && lignes.size() < MAX_EXERCICES) {
            double part = lignes.isEmpty() ? prorataPremiereAnnee : Math.min(1.0, anneesRestantes);
            double taux = Math.max(tauxDegressif * part, part / anneesRestantes);

            BigDecimal valeurDebut = valeurResiduelle;
            BigDecimal dotation = arrondir(valeurDebut.multiply(BigDecimal.valueOf(Math.min(taux, 1.0))));

            cumul = cumul.add(dotation);
            valeurResiduelle = base.subtract(cumul);

            lignes.add(ligne(exercice, valeurDebut, tauxEffectif(dotation, valeurDebut), dotation,
                    cumul, valeurResiduelle));

            anneesRestantes -= part;
            exercice++;
        }

        return ajusterDernierExercice(lignes, base);
    }

    /**
     * Coefficient fiscal du dégressif, ou {@code null} si la durée n'y ouvre pas
     * droit (moins de 3 ans) — le plan est alors traité en linéaire.
     */
    private BigDecimal coefficientDegressif(int dureeMois) {
        double annees = dureeMois / 12.0;
        if (annees < 3) {
            return null;
        }
        if (annees <= 4) {
            return BigDecimal.valueOf(1.25);
        }
        if (annees <= 6) {
            return BigDecimal.valueOf(1.75);
        }
        return BigDecimal.valueOf(2.25);
    }

    /** Le dernier exercice absorbe le résidu d'arrondi : le cumul égale la base. */
    private List<LigneAmortissementDto> ajusterDernierExercice(List<LigneAmortissementDto> lignes, BigDecimal base) {
        if (lignes.isEmpty()) {
            return lignes;
        }
        LigneAmortissementDto derniere = lignes.get(lignes.size() - 1);
        BigDecimal ecart = arrondir(base).subtract(derniere.getCumulAmortissements());

        if (ecart.signum() != 0) {
            derniere.setDotation(derniere.getDotation().add(ecart));
            derniere.setCumulAmortissements(arrondir(base));
        }
        derniere.setValeurNetteComptable(BigDecimal.ZERO.setScale(ECHELLE_MONETAIRE, RoundingMode.HALF_UP));
        return lignes;
    }

    /** Situation à la date demandée : cumul des exercices clos et VNC qui en découle. */
    private void appliquerSituation(PlanAmortissementDto plan,
                                    List<LigneAmortissementDto> lignes,
                                    BigDecimal base,
                                    LocalDate dateSituation) {
        BigDecimal cumul = BigDecimal.ZERO.setScale(ECHELLE_MONETAIRE, RoundingMode.HALF_UP);

        for (LigneAmortissementDto ligne : lignes) {
            if (ligne.getExercice() <= dateSituation.getYear()) {
                cumul = ligne.getCumulAmortissements();
            }
        }

        plan.setCumulAmortissements(cumul);
        plan.setValeurNetteComptable(arrondir(base).subtract(cumul));
    }

    private LigneAmortissementDto ligne(int exercice, BigDecimal valeurDebut, BigDecimal taux,
                                        BigDecimal dotation, BigDecimal cumul, BigDecimal vnc) {
        return new LigneAmortissementDto(exercice, arrondir(valeurDebut), taux,
                arrondir(dotation), arrondir(cumul), arrondir(vnc));
    }

    /** Taux réellement constaté sur l'exercice, en pourcentage de la valeur d'ouverture. */
    private BigDecimal tauxEffectif(BigDecimal dotation, BigDecimal valeurDebut) {
        if (valeurDebut == null || valeurDebut.signum() == 0) {
            return BigDecimal.ZERO.setScale(ECHELLE_MONETAIRE, RoundingMode.HALF_UP);
        }
        return dotation.multiply(BigDecimal.valueOf(100))
                .divide(valeurDebut, ECHELLE_MONETAIRE, RoundingMode.HALF_UP);
    }

    private BigDecimal arrondir(BigDecimal montant) {
        return montant.setScale(ECHELLE_MONETAIRE, RoundingMode.HALF_UP);
    }
}
