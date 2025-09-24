package smartPark.smart_park.models.dto.request;
// Adaptez
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReportSignRequestDto {
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    @NotNull
    private String password;
}