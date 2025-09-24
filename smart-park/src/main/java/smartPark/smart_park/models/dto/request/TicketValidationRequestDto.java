package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketValidationRequestDto {
    @NotNull(message = "La décision de validation est obligatoire")
    private Boolean valider;

    private String motifRejet;
    private String observations; // Champ supplémentaire du contrôleur
}