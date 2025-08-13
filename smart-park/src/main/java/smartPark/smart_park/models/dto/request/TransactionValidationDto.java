package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionValidationDto {

    @NotNull(message = "La décision de validation est obligatoire")
    private Boolean valider; // true pour valider, false pour rejeter

    @Size(max = 500, message = "Le motif de rejet ne peut pas dépasser 500 caractères")
    private String motifRejet;

    @Size(max = 500, message = "Les observations ne peuvent pas dépasser 500 caractères")
    private String observations;

    @NotNull(message = "L'ID du validateur est obligatoire")
    private Long validateurId;
}