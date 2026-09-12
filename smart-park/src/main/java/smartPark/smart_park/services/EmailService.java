package smartPark.smart_park.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String formEmail;

    public void sendOtpEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(formEmail);
        //message.setFrom("no-reply@smartpark.com"); // Peut être votre email d'envoi
        message.setTo(to);
        message.setSubject("Votre code de connexion SmartPark");
        message.setText("Bonjour,\n\nVotre code de vérification à usage unique est : " + code + "\n\nCe code expirera dans 15 minutes.\nMerci de ne pas repondre a ce message automatique🙏.");
        message.setReplyTo("no-reply@gmail.com");
        mailSender.send(message);
    }
}