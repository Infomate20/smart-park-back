package smartPark.smart_park.models.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImmobilisationLightDto {
    private Long id;
    private String codeImmobilisation;
    private String designation;

}
