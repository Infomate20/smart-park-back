package smartPark.smart_park.models.dto.request;

import org.hibernate.annotations.Check;
import smartPark.smart_park.models.entity.enums.EtatIntervention;
import smartPark.smart_park.models.entity.enums.Orientation;
import smartPark.smart_park.models.entity.enums.TypeIntervention;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterventionRequestDto {

    @NotBlank(message = "La description de l'intervention est obligatoire")
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;

    @NotNull(message = "La date d'intervention est obligatoire")
    private LocalDateTime dateIntervention;

    private BigDecimal coutIntervention;

    @Size(max = 500, message = "Les observations ne peuvent pas dépasser 500 caractères")
    private String observations;

    @NotNull(message = "Le type d'intervention est obligatoire")
    private TypeIntervention typeIntervention;

    private LocalDateTime dateCloture;
    @NotNull(message = "l'etat de l'intervention est obligatoire")
    private EtatIntervention etatIntervention;

    private Orientation orientation;

    @NotNull(message = "L'ID de l'immobilisation est obligatoire")
    private Long immobilisationId;

    @NotNull(message = "L'ID du technicien est obligatoire")
    private Long technicienId;
}

// InterventionResponseDto.java

