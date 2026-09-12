package smartPark.smart_park.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import smartPark.smart_park.exceptions.BusinessException;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.dto.request.DetailVehiculeRequestDto;
import smartPark.smart_park.models.dto.request.ImmobilisationRequestDto;
import smartPark.smart_park.models.dto.response.DetailVehiculeResponseDto;
import smartPark.smart_park.models.dto.response.ImmobilisationResponseDto;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.enums.EtatImmobilisation;
import smartPark.smart_park.models.entity.enums.TypeCarburant;
import smartPark.smart_park.repository.CategorieRepository;
import smartPark.smart_park.repository.DetailVehiculeRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Couvre le satellite véhicule : upsert, unicité de l'immatriculation,
 * indicateurs calculés et nettoyage de la clé étrangère à la suppression du bien.
 * Transactionnel : rien n'est conservé en base après l'exécution.
 */
@SpringBootTest
@Transactional
class DetailVehiculeTest {

    @Autowired
    private DetailVehiculeService detailVehiculeService;

    @Autowired
    private ImmobilisationService immobilisationService;

    @Autowired
    private DetailVehiculeRepository detailVehiculeRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    private Long creerVehicule(String designation) {
        Categorie vehicules = categorieRepository.findByCode("VEH").orElseThrow();

        ImmobilisationRequestDto dto = new ImmobilisationRequestDto();
        dto.setDesignation(designation);
        dto.setCategorieId(vehicules.getId());
        dto.setEtat(EtatImmobilisation.EN_SERVICE);
        dto.setActif(true);

        ImmobilisationResponseDto creee = immobilisationService.creerImmobilisation(dto);
        return creee.getId();
    }

    private DetailVehiculeRequestDto details(String immatriculation) {
        DetailVehiculeRequestDto dto = new DetailVehiculeRequestDto();
        dto.setImmatriculation(immatriculation);
        dto.setKilometrage(45_000);
        dto.setTypeCarburant(TypeCarburant.DIESEL);
        return dto;
    }

    @Test
    void attacheLesInformationsVehiculeAUnBien() {
        Long immobilisationId = creerVehicule("Toyota Hilux");

        DetailVehiculeResponseDto detail =
                detailVehiculeService.enregistrerDetailVehicule(immobilisationId, details("AB-123-CD"));

        assertThat(detail.getImmobilisationId()).isEqualTo(immobilisationId);
        assertThat(detail.getDesignation()).isEqualTo("Toyota Hilux");
        assertThat(detail.getTypeCarburantLibelle()).isEqualTo("Diesel");
    }

    @Test
    void normaliseLimmatriculation() {
        Long immobilisationId = creerVehicule("Renault Kangoo");

        DetailVehiculeResponseDto detail =
                detailVehiculeService.enregistrerDetailVehicule(immobilisationId, details(" ab 123 cd "));

        assertThat(detail.getImmatriculation()).isEqualTo("AB123CD");
    }

    @Test
    void remplaceLesInformationsAuLieuDenCreerUnSecond() {
        Long immobilisationId = creerVehicule("Peugeot Partner");

        DetailVehiculeResponseDto premier =
                detailVehiculeService.enregistrerDetailVehicule(immobilisationId, details("EF-456-GH"));

        DetailVehiculeRequestDto miseAJour = details("EF-456-GH");
        miseAJour.setKilometrage(72_000);
        DetailVehiculeResponseDto second =
                detailVehiculeService.enregistrerDetailVehicule(immobilisationId, miseAJour);

        assertThat(second.getId()).isEqualTo(premier.getId());
        assertThat(second.getKilometrage()).isEqualTo(72_000);
    }

    @Test
    void refuseUneImmatriculationDejaUtiliseeParUnAutreVehicule() {
        Long premier = creerVehicule("Dacia Duster");
        Long second = creerVehicule("Dacia Logan");

        detailVehiculeService.enregistrerDetailVehicule(premier, details("IJ-789-KL"));

        // Saisie sous une autre forme : ce doit rester la même plaque
        assertThatThrownBy(() -> detailVehiculeService.enregistrerDetailVehicule(second, details("ij789kl")))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("IJ789KL");
    }

    @Test
    void calculeLesIndicateursDecheance() {
        Long immobilisationId = creerVehicule("Ford Ranger");

        DetailVehiculeRequestDto dto = details("MN-012-OP");
        dto.setDateProchainControleTechnique(LocalDate.now().minusDays(1));
        dto.setDateExpirationAssurance(LocalDate.now().plusMonths(6));

        DetailVehiculeResponseDto detail =
                detailVehiculeService.enregistrerDetailVehicule(immobilisationId, dto);

        assertThat(detail.getControleTechniqueAJour()).isFalse();
        assertThat(detail.getAssuranceAJour()).isTrue();
    }

    @Test
    void listeLesVehiculesDeManierePaginee() {
        detailVehiculeService.enregistrerDetailVehicule(creerVehicule("Fiat Doblo"), details("UV-678-WX"));
        detailVehiculeService.enregistrerDetailVehicule(creerVehicule("Fiat Ducato"), details("YZ-901-AB"));

        Page<DetailVehiculeResponseDto> page =
                detailVehiculeService.obtenirTousLesVehicules(PageRequest.of(0, 1));

        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalElements()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void supprimerLimmobilisationSupprimeAussiSonSatellite() {
        Long immobilisationId = creerVehicule("Iveco Daily");
        detailVehiculeService.enregistrerDetailVehicule(immobilisationId, details("QR-345-ST"));

        immobilisationService.supprimerImmobilisation(immobilisationId);

        assertThat(detailVehiculeRepository.findByImmobilisationId(immobilisationId)).isEmpty();
        assertThatThrownBy(() -> immobilisationService.obtenirImmobilisationParId(immobilisationId))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
