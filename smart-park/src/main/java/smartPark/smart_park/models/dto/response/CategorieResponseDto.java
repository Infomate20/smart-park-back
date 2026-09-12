package smartPark.smart_park.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorieResponseDto {

    private Long id;
    private String nom;
    private String code;
    private String description;
    private Integer dureeAmortissementMois;
    private MethodeAmortissement methodeAmortissement;
    private Boolean actif;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private Long nombreImmobilisations;
}
