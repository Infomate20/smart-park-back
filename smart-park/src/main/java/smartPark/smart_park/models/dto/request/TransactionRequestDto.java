package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.TypeTransaction;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {

    @NotNull(message = "Le type de transaction est obligatoire")
    private TypeTransaction typeTransaction;

    @NotNull(message = "La date de demande est obligatoire")
    private LocalDateTime dateDemande;

    @Size(max = 1000, message = "Le motif ne peut pas dépasser 1000 caractères")
    private String motif;

    @Size(max = 500, message = "Les observations ne peuvent pas dépasser 500 caractères")
    private String observations;

    @NotNull(message = "L'ID de l'immobilisation est obligatoire")
    private Long immobilisationId;

    /**
     * Agences source et destination : leur caractère obligatoire dépend du type
     * de transaction, elles ne portent donc pas de {@code @NotNull} ici.
     *
     * <p>C'est {@code TransactionServiceImpl.validerAgencesSelonType} qui fait
     * autorité, avec un message adapté au type :
     * <ul>
     *   <li>TRANSFERT : les deux sont exigées, et doivent être différentes ;</li>
     *   <li>AFFECTATION : seule la destination est exigée ;</li>
     *   <li>DESAFFECTATION : seule la source est exigée.</li>
     * </ul>
     */
    private Long agenceSourceId;

    private Long agenceDestinationId;

    @NotNull(message = "L'ID du demandeur est obligatoire")
    private Long demandeurId;
}