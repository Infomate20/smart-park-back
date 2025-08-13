package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartPark.smart_park.models.dto.request.TransactionRequestDto;
import smartPark.smart_park.models.dto.request.TransactionUpdateDto;
import smartPark.smart_park.models.dto.request.TransactionValidationDto;
import smartPark.smart_park.models.dto.response.TransactionResponseDto;
import smartPark.smart_park.models.entity.enums.EtatTransaction;
import smartPark.smart_park.models.entity.enums.TypeTransaction;
import smartPark.smart_park.services.TransactionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<TransactionResponseDto> creerTransaction(
            @Valid @RequestBody TransactionRequestDto requestDto) {

        log.info("Demande de création de transaction pour l'immobilisation ID: {}", requestDto.getImmobilisationId());
        TransactionResponseDto response = transactionService.creerTransaction(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<TransactionResponseDto> obtenirTransactionParId(@PathVariable Long id) {

        log.info("Demande de récupération de la transaction ID: {}", id);
        TransactionResponseDto response = transactionService.obtenirTransactionParId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirToutesLesTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande de récupération de toutes les transactions - Page: {}, Size: {}, Sort: {} {}", page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirToutesLesTransactions(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionResponseDto>> obtenirToutesLesTransactionsSansPagination() {
        log.info("Demande de récupération de toutes les transactions sans pagination");
        List<TransactionResponseDto> response = transactionService.obtenirToutesLesTransactions();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<TransactionResponseDto> mettreAJourTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionUpdateDto updateDto) {

        log.info("Demande de mise à jour de la transaction ID: {}", id);
        TransactionResponseDto response = transactionService.mettreAJourTransaction(id, updateDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimerTransaction(@PathVariable Long id) {

        log.info("Demande de suppression de la transaction ID: {}", id);
        transactionService.supprimerTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/valider")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TransactionResponseDto> validerTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionValidationDto validationDto) {

        log.info("Demande de validation de la transaction ID: {}", id);
        TransactionResponseDto response = transactionService.validerTransaction(id, validationDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/annuler")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<TransactionResponseDto> annulerTransaction(
            @PathVariable Long id,
            @RequestParam Long utilisateurId) {

        log.info("Demande d'annulation de la transaction ID: {} par l'utilisateur ID: {}", id, utilisateurId);
        TransactionResponseDto response = transactionService.annulerTransaction(id, utilisateurId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/immobilisation/{immobilisationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParImmobilisation(
            @PathVariable Long immobilisationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions pour l'immobilisation ID: {}", immobilisationId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParImmobilisation(immobilisationId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demandeur/{demandeurId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParDemandeur(
            @PathVariable Long demandeurId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions pour le demandeur ID: {}", demandeurId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParDemandeur(demandeurId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validateur/{validateurId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParValidateur(
            @PathVariable Long validateurId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions pour le validateur ID: {}", validateurId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParValidateur(validateurId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/etat/{etatTransaction}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParEtat(
            @PathVariable EtatTransaction etatTransaction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions par état: {}", etatTransaction);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParEtat(etatTransaction, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{typeTransaction}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParType(
            @PathVariable TypeTransaction typeTransaction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions par type: {}", typeTransaction);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParType(typeTransaction, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/agence-source/{agenceSourceId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParAgenceSource(
            @PathVariable Long agenceSourceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions pour l'agence source ID: {}", agenceSourceId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParAgenceSource(agenceSourceId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/agence-destination/{agenceDestinationId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParAgenceDestination(
            @PathVariable Long agenceDestinationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions pour l'agence destination ID: {}", agenceDestinationId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParAgenceDestination(agenceDestinationId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/agence/{agenceId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsParAgenceImpliquee(
            @PathVariable Long agenceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Demande des transactions impliquant l'agence ID: {}", agenceId);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsParAgenceImpliquee(agenceId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periode-demande")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<List<TransactionResponseDto>> obtenirTransactionsParPeriodeDemande(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {

        log.info("Demande des transactions par période de demande entre {} et {}", dateDebut, dateFin);
        List<TransactionResponseDto> response = transactionService.obtenirTransactionsParPeriodeDemande(dateDebut, dateFin);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/periode-validation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionResponseDto>> obtenirTransactionsParPeriodeValidation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin) {

        log.info("Demande des transactions par période de validation entre {} et {}", dateDebut, dateFin);
        List<TransactionResponseDto> response = transactionService.obtenirTransactionsParPeriodeValidation(dateDebut, dateFin);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/en-attente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> obtenirTransactionsEnAttente(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        log.info("Demande des transactions en attente - Page: {}, Size: {}", page, size);

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.obtenirTransactionsEnAttente(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recherche")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Page<TransactionResponseDto>> rechercherAvecCriteres(
            @RequestParam(required = false) Long immobilisationId,
            @RequestParam(required = false) Long demandeurId,
            @RequestParam(required = false) Long validateurId,
            @RequestParam(required = false) EtatTransaction etatTransaction,
            @RequestParam(required = false) TypeTransaction typeTransaction,
            @RequestParam(required = false) Long agenceSourceId,
            @RequestParam(required = false) Long agenceDestinationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dateDemande") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        log.info("Recherche de transactions avec critères multiples");

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TransactionResponseDto> response = transactionService.rechercherAvecCriteres(
                immobilisationId, demandeurId, validateurId, etatTransaction, typeTransaction,
                agenceSourceId, agenceDestinationId, dateDebut, dateFin, pageable
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/immobilisation/{immobilisationId}/recentes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<List<TransactionResponseDto>> obtenirTransactionsRecentes(
            @PathVariable Long immobilisationId,
            @RequestParam(defaultValue = "5") int limite) {

        log.info("Demande des {} transactions les plus récentes pour l'immobilisation ID: {}", limite, immobilisationId);
        List<TransactionResponseDto> response = transactionService.obtenirTransactionsRecentes(immobilisationId, limite);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistiques/etats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> obtenirStatistiquesParEtat() {
        log.info("Demande des statistiques par état de transaction");
        List<Object[]> response = transactionService.obtenirStatistiquesParEtat();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/statistiques/types")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> obtenirStatistiquesParType() {
        log.info("Demande des statistiques par type de transaction");
        List<Object[]> response = transactionService.obtenirStatistiquesParType();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/existe")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Boolean> transactionExiste(@PathVariable Long id) {

        log.info("Vérification de l'existence de la transaction ID: {}", id);
        boolean existe = transactionService.transactionExiste(id);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/immobilisation/{immobilisationId}/en-attente")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT') or hasRole('TECHNICIEN')")
    public ResponseEntity<Boolean> hasTransactionsEnAttenteForImmobilisation(@PathVariable Long immobilisationId) {

        log.info("Vérification des transactions en attente pour l'immobilisation ID: {}", immobilisationId);
        boolean hasTransactions = transactionService.hasTransactionsEnAttenteForImmobilisation(immobilisationId);
        return ResponseEntity.ok(hasTransactions);
    }
}