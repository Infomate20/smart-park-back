package smartPark.smart_park.services.impl;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.entity.Immobilisation;
import smartPark.smart_park.repository.ImmobilisationRepository;
import smartPark.smart_park.services.EtiquetteService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EtiquetteImpl implements EtiquetteService {

    private final ImmobilisationRepository immobilisationRepository;

    /**
     * Génère un PDF formaté en étiquette pour une immobilisation donnée.
     * Le PDF inclut le nom, l'agence, le code-barres et le code de l'immobilisation.
     * @param immobilisationId L'ID de l'immobilisation pour laquelle générer l'étiquette.
     * @return Un tableau de bytes représentant le fichier PDF.
     * @throws IOException si une erreur d'I/O se produit.
     */
    public byte[] genererEtiquettePdf(Long immobilisationId) throws IOException {
        // 1. Récupérer les données de l'immobilisation
        Immobilisation immo = immobilisationRepository.findById(immobilisationId)
                .orElseThrow(() -> new ResourceNotFoundException("Immobilisation non trouvée avec l'ID: " + immobilisationId));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);

        // 2. Définir la taille de la page au format étiquette (ex: 80mm x 50mm)
        // Les dimensions sont en points (1mm = 2.83 points)
        float widthInPoints = 80 * 2.83f;
        float heightInPoints = 50 * 2.83f;
        PageSize etiquetteSize = new PageSize(widthInPoints, heightInPoints);
        Document document = new Document(pdf, etiquetteSize);
        // Définir des marges très faibles
        document.setMargins(5, 5, 5, 5);

        // 3. Ajouter le contenu à l'étiquette
        // Désignation en grand et centré
        document.add(new Paragraph(immo.getDesignation())
                .setBold()
                .setFontSize(14)
                .setMultipliedLeading(1.0f) // Réduit l'interligne
                .setTextAlignment(TextAlignment.CENTER));

        // Nom de l'agence
        String agenceNom = (immo.getAgence() != null) ? immo.getAgence().getNom() : "Siège";
        document.add(new Paragraph("Agence: " + agenceNom)
                .setFontSize(10)
                .setMultipliedLeading(1.0f));

        Barcode128 barcode = new Barcode128(pdf);
        barcode.setCodeType(Barcode128.CODE128);
        barcode.setCode(immo.getCodeImmobilisation()); // Le texte à encoder

        Image barcodeImage = new Image(barcode.createFormXObject(pdf));
        barcodeImage.setWidth(UnitValue.createPercentValue(80));
        barcodeImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(barcodeImage);

        document.add(new Paragraph(immo.getCodeImmobilisation())
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER));

        document.close();
        return baos.toByteArray();
    }
}
