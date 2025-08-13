package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.CategorieRequestDto;
import smartPark.smart_park.models.dto.response.CategorieResponseDto;
import smartPark.smart_park.services.CategorieService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class CategorieController {
    private final CategorieService categorieService;
    @PostMapping
    public ResponseEntity<CategorieResponseDto> creerCategorie(@Valid @RequestBody CategorieRequestDto requestDto) {
        CategorieResponseDto categorie = categorieService.creerCategorie(requestDto);
        return new ResponseEntity<>(categorie, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategorieResponseDto> obtenirCategorieParId(@PathVariable Long id) {
        CategorieResponseDto categorie = categorieService.obtenirCategorieParId(id);
        return ResponseEntity.ok(categorie);
    }
    @GetMapping
    public ResponseEntity<List<CategorieResponseDto>> obtenirToutesLesCategories() {
        List<CategorieResponseDto> categories = categorieService.obtenirToutesLesCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/actives")
    public ResponseEntity<List<CategorieResponseDto>> obtenirCategoriesActives() {
        List<CategorieResponseDto> categories = categorieService.obtenirCategoriesActives();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/paginated")
    public ResponseEntity<Page<CategorieResponseDto>> obtenirCategoriesAvecPagination(
            @RequestParam (required = false) String searchTerm,
            @RequestParam(required = false) Boolean actif,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CategorieResponseDto> categories = categorieService.obtenirCategoriesAvecPagination(searchTerm,actif,pageable);
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<CategorieResponseDto>> rechercherCategories(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CategorieResponseDto> categories = categorieService.rechercherCategories(searchTerm, pageable);
        return ResponseEntity.ok(categories);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategorieResponseDto> modifierCategorie(
            @PathVariable Long id,
            @Valid @RequestBody CategorieRequestDto requestDto) {
        CategorieResponseDto categorie = categorieService.modifierCategorie(id, requestDto);
        return ResponseEntity.ok(categorie);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCategorie(@PathVariable Long id) {
        categorieService.supprimerCategorie(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/desactiver")
    public ResponseEntity<Void> desactiverCategorie(@PathVariable Long id) {
        categorieService.desactiverCategorie(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/activer")
    public ResponseEntity<Void> activerCategorie(@PathVariable Long id) {
        categorieService.activerCategorie(id);
        return ResponseEntity.ok().build();
    }
}
