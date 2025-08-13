package smartPark.smart_park.mapper;

import org.springframework.stereotype.Component;
import smartPark.smart_park.models.dto.request.CategorieRequestDto;
import smartPark.smart_park.models.dto.response.CategorieResponseDto;
import smartPark.smart_park.models.entity.Categorie;
import  java.util.List;
import  java.util.stream.Collectors;

@Component
public class CategorieMapper {

    public Categorie toEntity(CategorieRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Categorie categorie = new Categorie();
        categorie.setNom(requestDto.getNom());
        categorie.setDescription(requestDto.getDescription());
        categorie.setActif(requestDto.getActif() != null ? requestDto.getActif() : true);

        return categorie;
    }

    public CategorieResponseDto toResponseDto(Categorie categorie) {
        if (categorie == null) {
            return null;
        }

        CategorieResponseDto responseDto = new CategorieResponseDto();
        responseDto.setId(categorie.getId());
        responseDto.setNom(categorie.getNom());
        responseDto.setDescription(categorie.getDescription());
        responseDto.setActif(categorie.getActif());
        responseDto.setDateCreation(categorie.getDateCreation());
        responseDto.setDateModification(categorie.getDateModification());

        // Calculer le nombre d'immobilisations
        Long nombreImmobilisations = (categorie.getImmobilisations() != null)
                ? (long) categorie.getImmobilisations().size()
                : 0L;
        responseDto.setNombreImmobilisations(nombreImmobilisations);

        return responseDto;
    }

    public List<CategorieResponseDto> toResponseDtoList(List<Categorie> categories) {
        if (categories == null) {
            return null;
        }

        return categories.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public void updateEntityFromDto(CategorieRequestDto requestDto, Categorie categorie) {
        if (requestDto == null || categorie == null) {
            return;
        }

        if (requestDto.getNom() != null) {
            categorie.setNom(requestDto.getNom());
        }
        if (requestDto.getDescription() != null) {
            categorie.setDescription(requestDto.getDescription());
        }
        if (requestDto.getActif() != null) {
            categorie.setActif(requestDto.getActif());
        }
    }
}