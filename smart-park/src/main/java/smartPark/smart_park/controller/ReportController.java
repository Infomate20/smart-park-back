package smartPark.smart_park.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.dto.request.ReportPreviewRequestDto;
import smartPark.smart_park.models.dto.request.ReportSignRequestDto;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.services.impl.ReportService;
import smartPark.smart_park.services.UtilisateurService;


import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/interventions/preview")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> previewInterventionsReport(@Valid @RequestBody ReportPreviewRequestDto reportRequest) throws IOException {
        byte[] pdfBytes = reportService.generateInterventionsReport(reportRequest.getDateDebut(), reportRequest.getDateFin(), null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "preview_interventions.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/interventions/signed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> getSignedInterventionsReport(@Valid @RequestBody ReportSignRequestDto reportRequest, Authentication authentication) throws IOException {
        String userEmail = authentication.getName();
        Utilisateur signataire = utilisateurService.findInfoUser(userEmail) // Assurez-vous d'avoir cette méthode
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé: " + userEmail));

        if (!passwordEncoder.matches(reportRequest.getPassword(), signataire.getMotDePasse())) {
            throw new BadCredentialsException("Mot de passe de signature invalide");
        }

        byte[] pdfBytes = reportService.generateInterventionsReport(reportRequest.getDateDebut(), reportRequest.getDateFin(), signataire);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "Rapport_Interventions_" + LocalDate.now() + ".pdf";
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/transactions/preview")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> previewTransactionsReport(@Valid @RequestBody ReportPreviewRequestDto reportRequest) throws IOException {
        byte[] pdfBytes = reportService.generateTransactionsReport(reportRequest.getDateDebut(), reportRequest.getDateFin(), null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "preview_transactions.pdf");
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/transactions/signed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> getSignedTransactionsReport(@Valid @RequestBody ReportSignRequestDto reportRequest, Authentication authentication) throws IOException {
        String userEmail = authentication.getName();
        Utilisateur signataire = utilisateurService.findInfoUser(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + userEmail));

        if (!passwordEncoder.matches(reportRequest.getPassword(), signataire.getMotDePasse())) {
            throw new BadCredentialsException("Mot de passe de signature invalide");
        }

        byte[] pdfBytes = reportService.generateTransactionsReport(reportRequest.getDateDebut(), reportRequest.getDateFin(), signataire);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "Rapport_Transactions_" + LocalDate.now() + ".pdf";
        headers.setContentDispositionFormData("attachment", filename);
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
