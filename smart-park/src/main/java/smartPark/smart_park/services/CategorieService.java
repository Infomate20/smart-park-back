package smartPark.smart_park.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import smartPark.smart_park.models.dto.request.CategorieRequestDto;
import smartPark.smart_park.models.dto.response.CategorieResponseDto;

import java.util.List;

public interface CategorieService {

        CategorieResponseDto creerCategorie(CategorieRequestDto requestDto);

        CategorieResponseDto obtenirCategorieParId(Long id);

        List<CategorieResponseDto> obtenirToutesLesCategories();

        List<CategorieResponseDto> obtenirCategoriesActives();

        Page<CategorieResponseDto> obtenirCategoriesAvecPagination(String searchTerm,Boolean actif,Pageable pageable);

        Page<CategorieResponseDto> rechercherCategories(String searchTerm, Pageable pageable);

        CategorieResponseDto modifierCategorie(Long id, CategorieRequestDto requestDto);

        void supprimerCategorie(Long id);

        void desactiverCategorie(Long id);

        void activerCategorie(Long id);
    }

