package smartPark.smart_park.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.DetailVehiculeRequestDto;
import smartPark.smart_park.models.dto.response.DetailVehiculeResponseDto;
import smartPark.smart_park.models.entity.enums.TypeCarburant;
import smartPark.smart_park.services.DetailVehiculeService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Véhicules", description = "Informations propres aux immobilisations de type véhicule")
public class DetailVehiculeController {

    private final DetailVehiculeService detailVehiculeService;

    @PutMapping("/immobilisations/{immobilisationId}/vehicule")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIEN')")
    @Operation(summary = "Créer ou remplacer les informations véhicule d'une immobilisation")
    public ResponseEntity<DetailVehiculeResponseDto> enregistrerDetailVehicule(
            @PathVariable Long immobilisationId,
            @Valid @RequestBody DetailVehiculeRequestDto requestDto) {
        return ResponseEntity.ok(detailVehiculeService.enregistrerDetailVehicule(immobilisationId, requestDto));
    }

    @GetMapping("/immobilisations/{immobilisationId}/vehicule")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtenir les informations véhicule d'une immobilisation")
    public ResponseEntity<DetailVehiculeResponseDto> obtenirDetailVehicule(@PathVariable Long immobilisationId) {
        return ResponseEntity.ok(detailVehiculeService.obtenirDetailVehicule(immobilisationId));
    }

    @DeleteMapping("/immobilisations/{immobilisationId}/vehicule")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer les informations véhicule d'une immobilisation")
    public ResponseEntity<Void> supprimerDetailVehicule(@PathVariable Long immobilisationId) {
        detailVehiculeService.supprimerDetailVehicule(immobilisationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/vehicules")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lister les véhicules du parc, paginés")
    public ResponseEntity<Page<DetailVehiculeResponseDto>> obtenirTousLesVehicules(
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(detailVehiculeService.obtenirTousLesVehicules(pageable));
    }

    @GetMapping("/vehicules/immatriculation/{immatriculation}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrouver un véhicule par son immatriculation")
    public ResponseEntity<DetailVehiculeResponseDto> obtenirParImmatriculation(@PathVariable String immatriculation) {
        return ResponseEntity.ok(detailVehiculeService.obtenirParImmatriculation(immatriculation));
    }

    @GetMapping("/vehicules/carburants")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtenir les types de carburant avec leurs libellés")
    public ResponseEntity<List<Map<String, String>>> obtenirTypesCarburant() {
        List<Map<String, String>> carburants = Arrays.stream(TypeCarburant.values())
                .map(type -> Map.of("valeur", type.name(), "libelle", type.getLibelle()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(carburants);
    }
}
