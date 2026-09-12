package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginStep1ResponseDto {
    private String message;
    private Long userId;
}