package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequestDto {
    @NotNull(message = "L'ID de l'immobilisation ne peut pas être nul")
    private Long immobilisationId;

    @NotBlank(message = "La description du problème est obligatoire")
    @Size(min = 10, message = "La description doit contenir au moins 10 caractères")
    private String descriptionProbleme;
}