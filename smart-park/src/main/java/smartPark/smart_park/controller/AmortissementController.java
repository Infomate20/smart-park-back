package smartPark.smart_park.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.response.PlanAmortissementDto;
import smartPark.smart_park.services.AmortissementService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Amortissements", description = "Plans et situations d'amortissement des immobilisations")
public class AmortissementController {

    private final AmortissementService amortissementService;

    @GetMapping("/immobilisations/{immobilisationId}/amortissement")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Plan d'amortissement complet d'un bien, exercice par exercice")
    public ResponseEntity<PlanAmortissementDto> obtenirPlan(@PathVariable Long immobilisationId) {
        return ResponseEntity.ok(amortissementService.calculerPlan(immobilisationId));
    }

    @GetMapping("/immobilisations/{immobilisationId}/amortissement/situation")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Cumul et valeur nette comptable d'un bien à une date donnée")
    public ResponseEntity<PlanAmortissementDto> obtenirSituation(
            @PathVariable Long immobilisationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(amortissementService.calculerSituation(immobilisationId, date));
    }

    @GetMapping("/amortissements/synthese")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    @Operation(summary = "Synthèse paginée du parc : cumul et VNC de chaque bien à une date donnée")
    public ResponseEntity<Page<PlanAmortissementDto>> obtenirSyntheseParc(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(amortissementService.obtenirSyntheseParc(date, pageable));
    }
}
