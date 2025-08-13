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

    @NotNull(message="l'agence source est obligatoire")
    private Long agenceSourceId;

    @NotNull(message=" l'agence de destination est obligatoire")
    private Long agenceDestinationId;

    @NotNull(message = "L'ID du demandeur est obligatoire")
    private Long demandeurId;
}