package smartPark.smart_park.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

    @Service
    public class QRCodeGeneratorService {

        /**
         * Génère une image de QR Code à partir d'un texte.
         *
         * @param text   Le texte à encoder dans le QR Code.
         * @param width  La largeur de l'image.
         * @param height La hauteur de l'image.
         * @return Un tableau de bytes représentant l'image PNG du QR Code.
         */
        public byte[] generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            return pngOutputStream.toByteArray();
        }
    }
