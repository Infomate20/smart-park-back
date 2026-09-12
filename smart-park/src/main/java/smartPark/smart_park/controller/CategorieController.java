package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.CategorieRequestDto;
import smartPark.smart_park.models.dto.response.CategorieResponseDto;
import smartPark.smart_park.services.CategorieService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor

public class CategorieController {
    private final CategorieService categorieService;
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategorieResponseDto> creerCategorie(@Valid @RequestBody CategorieRequestDto requestDto) {
        CategorieResponseDto categorie = categorieService.creerCategorie(requestDto);
        return new ResponseEntity<>(categorie, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CategorieResponseDto> obtenirCategorieParId(@PathVariable Long id) {
        CategorieResponseDto categorie = categorieService.obtenirCategorieParId(id);
        return ResponseEntity.ok(categorie);
    }
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategorieResponseDto>> obtenirToutesLesCategories() {
        List<CategorieResponseDto> categories = categorieService.obtenirToutesLesCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/actives")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CategorieResponseDto>> obtenirCategoriesActives() {
        List<CategorieResponseDto> categories = categorieService.obtenirCategoriesActives();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/paginated")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CategorieResponseDto>> obtenirCategoriesAvecPagination(
            @RequestParam (required = false) String searchTerm,
            @RequestParam(required = false) Boolean actif,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CategorieResponseDto> categories = categorieService.obtenirCategoriesAvecPagination(searchTerm,actif,pageable);
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CategorieResponseDto>> rechercherCategories(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<CategorieResponseDto> categories = categorieService.rechercherCategories(searchTerm, pageable);
        return ResponseEntity.ok(categories);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategorieResponseDto> modifierCategorie(
            @PathVariable Long id,
            @Valid @RequestBody CategorieRequestDto requestDto) {
        CategorieResponseDto categorie = categorieService.modifierCategorie(id, requestDto);
        return ResponseEntity.ok(categorie);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerCategorie(@PathVariable Long id) {
        categorieService.supprimerCategorie(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/desactiver")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desactiverCategorie(@PathVariable Long id) {
        categorieService.desactiverCategorie(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}/activer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activerCategorie(@PathVariable Long id) {
        categorieService.activerCategorie(id);
        return ResponseEntity.ok().build();
    }
}
