package smartPark.smart_park.services.impl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartPark.smart_park.exceptions.EmptyReportException;
import smartPark.smart_park.models.entity.Intervention;
import smartPark.smart_park.models.entity.Transaction;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.repository.InterventionRepository;
import smartPark.smart_park.repository.TransactionRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final InterventionRepository interventionRepository;
    private final QRCodeGeneratorService qrCodeGeneratorService;
    private final TransactionRepository transactionRepository;


    /**
     * Génère un rapport d'interventions en PDF.
     * Si le signataire est null, génère une version de prévisualisation.
     * Sinon, génère une version signée avec un QR Code.
     * @param dateDebut La date de début de la période.
     * @param dateFin La date de fin de la période.
     * @param signataire L'utilisateur qui signe le document (peut être null).
     * @return Un tableau de bytes représentant le fichier PDF.
     */
    public byte[] generateInterventionsReport(LocalDate dateDebut, LocalDate dateFin, Utilisateur signataire) throws IOException {
        // 1. Récupérer les données depuis la base
        // Note : Vous devez créer cette méthode dans votre InterventionRepository
        List<Intervention> interventions = interventionRepository.findByDateInterventionBetween(
                dateDebut.atStartOfDay(),
                dateFin.atTime(LocalTime.MAX)
        );

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 2. Construire le contenu du PDF
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        document.add(new Paragraph("Rapport d'Interventions").setFontSize(20).setBold());
        document.add(new Paragraph("Période du " + dateDebut.format(dateFormatter) + " au " + dateFin.format(dateFormatter)));
        document.add(new Paragraph("\n"));

        // Création de la table
        Table table = new Table(new float[]{3, 5, 4, 3}); // 4 colonnes avec des largeurs relatives
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("Date");
        table.addHeaderCell("Immobilisation");
        table.addHeaderCell("Technicien");
        table.addHeaderCell("Type");

        // Remplissage de la table
        for (Intervention intervention : interventions) {
            table.addCell(intervention.getDateIntervention().format(dateFormatter));
            table.addCell(intervention.getImmobilisation().getDesignation());
            table.addCell(intervention.getTechnicien().getNomUtilisateur());
            table.addCell(intervention.getTypeIntervention().getDescription());
        }
        document.add(table);
        document.add(new Paragraph("\n\n"));

        // 3. Ajouter le bloc de signature (si nécessaire)
        if (signataire != null) {
            try {
                String signatureText = String.format("Signé par : %s\nEmail : %s\nDate : %s",
                        signataire.getNomUtilisateur(),
                        signataire.getEmail(),
                        LocalDate.now().format(dateFormatter));

                byte[] qrCodeImage = qrCodeGeneratorService.generateQRCodeImage(signatureText, 120, 120);

                document.add(new Image(ImageDataFactory.create(qrCodeImage)).scaleToFit(100, 100));
                document.add(new Paragraph("Signé numériquement par : " + signataire.getNomUtilisateur()));
            } catch (Exception e) {
                // Gérer l'exception de génération du QR Code
                document.add(new Paragraph("Erreur lors de la génération de la signature QR Code.").setFontColor(ColorConstants.RED));
            }
        } else {
            document.add(new Paragraph("Document non signé - PRÉVISUALISATION").setFontColor(ColorConstants.RED).setBold());
        }

        document.close();
        return baos.toByteArray();
    }

    // raport de transaction

    /**
     * Génère un rapport de transactions en PDF, avec vérification des données vides.
     * @throws EmptyReportException si aucune transaction n'est trouvée pour la période donnée.
     */
    public byte[] generateTransactionsReport(LocalDate dateDebut, LocalDate dateFin, Utilisateur signataire) throws IOException, EmptyReportException {
        // 1. Récupérer les données
        List<Transaction> transactions = transactionRepository.findAllByDateDemandeBetween(
                dateDebut.atStartOfDay(),
                dateFin.atTime(LocalTime.MAX)
        );

        // --- GESTION DU RAPPORT VIDE ---
        if (transactions.isEmpty()) {
            throw new EmptyReportException("Aucune transaction trouvée pour la période sélectionnée.");
        }

        // Le reste de la méthode est identique à la version précédente...
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Table table = new Table(new float[]{3, 5, 3, 3, 3, 3}); // 6 colonnes
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("Date Demande");
        table.addHeaderCell("Immobilisation");
        table.addHeaderCell("Source");
        table.addHeaderCell("Destination");
        table.addHeaderCell("État");
        table.addHeaderCell("Demandeur");

        // Remplissage de la table
        for (Transaction transaction : transactions) {
            table.addCell(transaction.getDateDemande().format(dateFormatter));
            table.addCell(transaction.getImmobilisation().getDesignation());
            table.addCell(transaction.getAgenceSource().getNom());
            table.addCell(transaction.getAgenceDestination().getNom());
            table.addCell(new Cell().add(new Paragraph(transaction.getEtatTransaction().name()).setFontSize(8)));
            table.addCell(transaction.getDemandeur().getNomUtilisateur());
        }
        document.add(table);
        document.add(new Paragraph("\n\n"));

        // 3. Ajouter le bloc de signature (logique réutilisée)
        if (signataire != null) {
            try {
                String signatureText = String.format("Signé par : %s\nEmail : %s\nDate : %s",
                        signataire.getNomUtilisateur(),
                        signataire.getEmail(),
                        LocalDate.now().format(dateFormatter));
                byte[] qrCodeImage = qrCodeGeneratorService.generateQRCodeImage(signatureText, 120, 120);
                document.add(new Image(ImageDataFactory.create(qrCodeImage)).scaleToFit(100, 100));
                document.add(new Paragraph("Signé numériquement par : " + signataire.getNomUtilisateur()));
            } catch (Exception e) {
                document.add(new Paragraph("Erreur lors de la génération de la signature.").setFontColor(ColorConstants.RED));
            }
        } else {
            document.add(new Paragraph("Document non signé - PRÉVISUALISATION").setFontColor(ColorConstants.RED).setBold());
        }


        document.close();
        return baos.toByteArray();
    }
}