package smartPark.smart_park.mapper;

import org.springframework.stereotype.Component;
import smartPark.smart_park.models.dto.request.CategorieRequestDto;
import smartPark.smart_park.models.dto.response.CategorieResponseDto;
import smartPark.smart_park.models.entity.Categorie;
import smartPark.smart_park.models.entity.enums.MethodeAmortissement;
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
        categorie.setCode(normaliserCode(requestDto.getCode()));
        categorie.setDescription(requestDto.getDescription());
        MethodeAmortissement methode = requestDto.getMethodeAmortissement() != null
                ? requestDto.getMethodeAmortissement()
                : MethodeAmortissement.LINEAIRE;
        categorie.setMethodeAmortissement(methode);
        // Une catégorie non amortissable ne conserve pas de durée
        categorie.setDureeAmortissementMois(methode == MethodeAmortissement.NON_AMORTISSABLE
                ? null
                : requestDto.getDureeAmortissementMois());
        categorie.setActif(requestDto.getActif() != null ? requestDto.getActif() : true);

        return categorie;
    }

    /**
     * Les codes de catégorie sont stockés en majuscules et sans espace afin que
     * l'unicité et les codes d'immobilisation dérivés soient insensibles à la casse.
     */
    public String normaliserCode(String code) {
        return code != null ? code.trim().toUpperCase() : null;
    }

    public CategorieResponseDto toResponseDto(Categorie categorie) {
        if (categorie == null) {
            return null;
        }

        CategorieResponseDto responseDto = new CategorieResponseDto();
        responseDto.setId(categorie.getId());
        responseDto.setNom(categorie.getNom());
        responseDto.setCode(categorie.getCode());
        responseDto.setDescription(categorie.getDescription());
        responseDto.setDureeAmortissementMois(categorie.getDureeAmortissementMois());
        responseDto.setMethodeAmortissement(categorie.getMethodeAmortissement());
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
        if (requestDto.getCode() != null) {
            categorie.setCode(normaliserCode(requestDto.getCode()));
        }
        if (requestDto.getDescription() != null) {
            categorie.setDescription(requestDto.getDescription());
        }
        if (requestDto.getDureeAmortissementMois() != null) {
            categorie.setDureeAmortissementMois(requestDto.getDureeAmortissementMois());
        }
        if (requestDto.getMethodeAmortissement() != null) {
            categorie.setMethodeAmortissement(requestDto.getMethodeAmortissement());
            // Une catégorie non amortissable ne conserve pas de durée résiduelle
            if (requestDto.getMethodeAmortissement() == MethodeAmortissement.NON_AMORTISSABLE) {
                categorie.setDureeAmortissementMois(null);
            }
        }
        if (requestDto.getActif() != null) {
            categorie.setActif(requestDto.getActif());
        }
    }
}