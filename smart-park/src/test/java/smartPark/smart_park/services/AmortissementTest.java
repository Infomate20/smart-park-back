package smartPark.smart_park.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.response.LigneAmortissementDto;
import smartPark.smart_park.models.dto.response.PlanAmortissementDto;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;
import smartPark.smart_park.repository.CategorieRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Couvre le calcul des plans d'amortissement : linéaire avec et sans prorata,
 * dégressif avec bascule en linéaire, et les cas non amortissables.
 * Transactionnel : rien n'est conservé en base après l'exécution.
 */
@SpringBootTest
@Transactional
class AmortissementTest {

    @Autowired
    private AmortissementService amortissementService;

    @Autowired
    private ImmobilisationService immobilisationService;

    @Autowired
    private CategorieRepository categorieRepository;

    private Long creerBien(String codeCategorie, BigDecimal prix, LocalDate miseEnService) {
        Categorie categorie = categorieRepository.findByCode(codeCategorie).orElseThrow();

        ImmobilisationRequestDto dto = new ImmobilisationRequestDto();
        dto.setDesignation("Bien de test");
        dto.setCategorieId(categorie.getId());
        dto.setEtat(EtatImmobilisation.EN_SERVICE);
        dto.setActif(true);
        dto.setPrixAcquisition(prix);
        dto.setDateMiseEnService(miseEnService);

        return immobilisationService.creerImmobilisation(dto).getId();
    }

    @Test
    void lineaireSurExercicesPleins() {
        // INFO : 36 mois, linéaire. Mise en service au 1er janvier => 3 exercices pleins.
        Long id = creerBien("INFO", new BigDecimal("3600.00"), LocalDate.of(2024, 1, 1));

        PlanAmortissementDto plan = amortissementService.calculerPlan(id);

        assertThat(plan.isAmortissable()).isTrue();
        assertThat(plan.getLignes()).hasSize(3);
        assertThat(plan.getLignes()).extracting(LigneAmortissementDto::getDotation)
                .containsExactly(new BigDecimal("1200.00"), new BigDecimal("1200.00"), new BigDecimal("1200.00"));
        assertThat(plan.getLignes().get(2).getValeurNetteComptable()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void lineaireAvecProrataDeLaPremiereAnnee() {
        // Mise en service au 1er juillet : 6 mois sur le premier exercice,
        // le plan déborde donc sur un quatrième exercice.
        Long id = creerBien("INFO", new BigDecimal("3600.00"), LocalDate.of(2024, 7, 1));

        PlanAmortissementDto plan = amortissementService.calculerPlan(id);

        assertThat(plan.getLignes()).hasSize(4);
        assertThat(plan.getLignes()).extracting(LigneAmortissementDto::getDotation)
                .containsExactly(new BigDecimal("600.00"), new BigDecimal("1200.00"),
                        new BigDecimal("1200.00"), new BigDecimal("600.00"));
    }

    @Test
    void leCumulEgaleToujoursExactementLaBase() {
        // 1000 / 36 ne tombe pas juste : le dernier exercice absorbe le résidu.
        Long id = creerBien("INFO", new BigDecimal("1000.00"), LocalDate.of(2024, 3, 1));

        PlanAmortissementDto plan = amortissementService.calculerPlan(id);
        List<LigneAmortissementDto> lignes = plan.getLignes();

        assertThat(lignes.get(lignes.size() - 1).getCumulAmortissements())
                .isEqualByComparingTo(new BigDecimal("1000.00"));
        assertThat(lignes.get(lignes.size() - 1).getValeurNetteComptable())
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void degressifAvecBasculeEnLineaire() {
        // VEH : 60 mois => 5 ans, coefficient 1,75, taux dégressif 35 %.
        Categorie vehicules = categorieRepository.findByCode("VEH").orElseThrow();
        vehicules.setMethodeAmortissement(MethodeAmortissement.DEGRESSIF);
        categorieRepository.save(vehicules);

        Long id = creerBien("VEH", new BigDecimal("10000.00"), LocalDate.of(2024, 1, 1));

        PlanAmortissementDto plan = amortissementService.calculerPlan(id);

        assertThat(plan.getCoefficientDegressif()).isEqualByComparingTo(new BigDecimal("1.75"));
        assertThat(plan.getLignes()).hasSize(5);
        // 10000 x 35 %
        assertThat(plan.getLignes().get(0).getDotation()).isEqualByComparingTo(new BigDecimal("3500.00"));
        // 4e exercice : le taux linéaire sur la durée restante (50 %) l'emporte
        assertThat(plan.getLignes().get(3).getTaux()).isEqualByComparingTo(new BigDecimal("50.00"));
        assertThat(plan.getLignes().get(4).getCumulAmortissements())
                .isEqualByComparingTo(new BigDecimal("10000.00"));
    }

    @Test
    void situationAUneDateDonnee() {
        Long id = creerBien("INFO", new BigDecimal("3600.00"), LocalDate.of(2024, 1, 1));

        PlanAmortissementDto situation =
                amortissementService.calculerSituation(id, LocalDate.of(2025, 12, 31));

        assertThat(situation.getCumulAmortissements()).isEqualByComparingTo(new BigDecimal("2400.00"));
        assertThat(situation.getValeurNetteComptable()).isEqualByComparingTo(new BigDecimal("1200.00"));
        assertThat(situation.getLignes()).isNull();
    }

    @Test
    void signaleUneCategorieNonAmortissable() {
        Categorie mobilier = categorieRepository.findByCode("MOB").orElseThrow();
        mobilier.setMethodeAmortissement(MethodeAmortissement.NON_AMORTISSABLE);
        mobilier.setDureeAmortissementMois(null);
        categorieRepository.save(mobilier);

        Long id = creerBien("MOB", new BigDecimal("500.00"), LocalDate.of(2024, 1, 1));

        PlanAmortissementDto plan = amortissementService.calculerPlan(id);

        assertThat(plan.isAmortissable()).isFalse();
        assertThat(plan.getMotifNonAmortissable()).contains("non amortissable");
        assertThat(plan.getValeurNetteComptable()).isEqualByComparingTo(new BigDecimal("500.00"));
    }

    @Test
    void signaleUnPrixDacquisitionManquant() {
        Long id = creerBien("INFO", null, LocalDate.of(2024, 1, 1));

        PlanAmortissementDto plan = amortissementService.calculerPlan(id);

        assertThat(plan.isAmortissable()).isFalse();
        assertThat(plan.getMotifNonAmortissable()).contains("prix d'acquisition");
    }
}
