package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.TypeTransaction;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionUpdateDto {

    private TypeTransaction typeTransaction;

    private LocalDateTime dateDemande;

    @Size(max = 1000, message = "Le motif ne peut pas dépasser 1000 caractères")
    private String motif;

    @Size(max = 500, message = "Les observations ne peuvent pas dépasser 500 caractères")
    private String observations;

    private Long immobilisationId;

    private Long agenceSourceId;

    private Long agenceDestinationId;

    private Long demandeurId;
}