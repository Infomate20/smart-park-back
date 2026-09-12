package smartPark.smart_park.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import smartPark.smart_park.models.dto.request.TransactionRequestDto;
import smartPark.smart_park.models.entity.enums.TypeTransaction;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Les agences source et destination ne sont plus exigées inconditionnellement
 * par le DTO : leur obligation dépend du type de transaction, et c'est
 * {@code TransactionServiceImpl.validerAgencesSelonType} qui l'applique.
 *
 * <p>Ces contraintes ne s'exercent qu'à la frontière du contrôleur (@Valid),
 * jamais lors d'un appel direct au service : elles se testent donc sur le
 * validateur lui-même.
 */
class TransactionRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void initialiserValidateur() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    private TransactionRequestDto demande(TypeTransaction type) {
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setTypeTransaction(type);
        dto.setDateDemande(LocalDateTime.now());
        dto.setImmobilisationId(1L);
        dto.setDemandeurId(1L);
        return dto;
    }

    private Set<String> champsEnErreur(TransactionRequestDto dto) {
        return validator.validate(dto).stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Object::toString)
                .collect(java.util.stream.Collectors.toSet());
    }

    @Test
    void uneDesaffectationNexigePasLagenceDeDestination() {
        TransactionRequestDto dto = demande(TypeTransaction.DESAFFECTATION);
        dto.setAgenceSourceId(1L);

        assertThat(champsEnErreur(dto)).isEmpty();
    }

    @Test
    void uneAffectationNexigePasLagenceSource() {
        TransactionRequestDto dto = demande(TypeTransaction.AFFECTATION);
        dto.setAgenceDestinationId(2L);

        assertThat(champsEnErreur(dto)).isEmpty();
    }

    @Test
    void lesChampsReellementObligatoiresRestentControles() {
        TransactionRequestDto dto = new TransactionRequestDto();

        assertThat(champsEnErreur(dto))
                .containsExactlyInAnyOrder("typeTransaction", "dateDemande", "immobilisationId", "demandeurId");
    }
}
