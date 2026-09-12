package smartPark.smart_park.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.response.ImmobilisationResponseDto;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;
import smartPark.smart_park.repository.CategorieRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Vérifie que les biens sans numéro de série constructeur (mobilier) sont
 * créables et reçoivent un code d'immobilisation dérivé du code de catégorie.
 * Transactionnel : rien n'est conservé en base après l'exécution.
 */
@SpringBootTest
@Transactional
class ImmobilisationSansNumeroSerieTest {

    @Autowired
    private ImmobilisationService immobilisationService;

    @Autowired
    private CategorieRepository categorieRepository;

    private ImmobilisationRequestDto chaise(Long categorieId) {
        ImmobilisationRequestDto dto = new ImmobilisationRequestDto();
        dto.setDesignation("Chaise de bureau");
        dto.setCategorieId(categorieId);
        dto.setEtat(EtatImmobilisation.EN_SERVICE);
        dto.setActif(true);
        return dto;
    }

    @Test
    void creeUnBienSansNumeroSerieAvecUnCodeDeriveDeLaCategorie() {
        Categorie mobilier = categorieRepository.findByCode("MOB").orElseThrow();

        ImmobilisationResponseDto creee = immobilisationService.creerImmobilisation(chaise(mobilier.getId()));

        assertThat(creee.getNumeroSerie()).isNull();
        assertThat(creee.getCodeImmobilisation()).startsWith("STOCK-MOB-");
    }

    @Test
    void distingueDeuxBiensIdentiquesSansNumeroSerie() {
        Categorie mobilier = categorieRepository.findByCode("MOB").orElseThrow();

        ImmobilisationResponseDto premiere = immobilisationService.creerImmobilisation(chaise(mobilier.getId()));
        ImmobilisationResponseDto seconde = immobilisationService.creerImmobilisation(chaise(mobilier.getId()));

        assertThat(seconde.getCodeImmobilisation()).isNotEqualTo(premiere.getCodeImmobilisation());
    }

    @Test
    void conserveLeCodeBaseSurLeNumeroSerieQuandIlEstFourni() {
        Categorie informatique = categorieRepository.findByCode("INFO").orElseThrow();

        ImmobilisationRequestDto dto = chaise(informatique.getId());
        dto.setDesignation("Poste de travail");
        dto.setNumeroSerie("SN-TEST-0001");

        ImmobilisationResponseDto creee = immobilisationService.creerImmobilisation(dto);

        // Comportement historique inchangé : le code reste dérivé du numéro de série
        assertThat(creee.getCodeImmobilisation()).startsWith("STOCK-SN-TEST-0001-");
    }

    @Test
    void signaleLaDisponibiliteDunNumeroDeSerie() {
        Categorie informatique = categorieRepository.findByCode("INFO").orElseThrow();

        ImmobilisationRequestDto dto = chaise(informatique.getId());
        dto.setNumeroSerie("SN-DISPO-0001");
        ImmobilisationResponseDto creee = immobilisationService.creerImmobilisation(dto);

        assertThat(immobilisationService.numeroSerieDisponible("SN-DISPO-0001", null)).isFalse();
        assertThat(immobilisationService.numeroSerieDisponible("SN-DISPO-0002", null)).isTrue();
        // Exclusion du bien lui-même : cas de la modification
        assertThat(immobilisationService.numeroSerieDisponible("SN-DISPO-0001", creee.getId())).isTrue();
        // Un bien non sérialisé n'entre jamais en collision
        assertThat(immobilisationService.numeroSerieDisponible("  ", null)).isTrue();
    }

    @Test
    void signaleLaDisponibiliteDunCodeImmobilisation() {
        Categorie mobilier = categorieRepository.findByCode("MOB").orElseThrow();
        ImmobilisationResponseDto creee = immobilisationService.creerImmobilisation(chaise(mobilier.getId()));

        assertThat(immobilisationService.codeImmobilisationDisponible(creee.getCodeImmobilisation(), null)).isFalse();
        assertThat(immobilisationService.codeImmobilisationDisponible(creee.getCodeImmobilisation(), creee.getId())).isTrue();
        assertThat(immobilisationService.codeImmobilisationDisponible("CODE-INEXISTANT", null)).isTrue();
    }
}
