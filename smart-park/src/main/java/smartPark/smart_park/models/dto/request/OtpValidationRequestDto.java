package smartPark.smart_park.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpValidationRequestDto {
    @NotNull
    private Long userId;
    @NotBlank
    private String code;
}