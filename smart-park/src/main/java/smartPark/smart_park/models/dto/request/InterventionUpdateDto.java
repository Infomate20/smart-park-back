package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.EtatIntervention;
import smartPark.smart_park.models.entity.enums.Orientation;
import smartPark.smart_park.models.entity.enums.TypeIntervention;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterventionUpdateDto {

    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;

    private LocalDateTime dateIntervention;

    private BigDecimal coutIntervention;

    @Size(max = 500, message = "Les observations ne peuvent pas dépasser 500 caractères")
    private String observations;

    private TypeIntervention typeIntervention;

    private Orientation orientation;

    private EtatIntervention etatIntervention;

    private LocalDateTime dateCloture;

    private Long immobilisationId;

    private Long technicienId;
}