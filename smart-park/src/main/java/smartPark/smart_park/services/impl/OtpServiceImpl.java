package smartPark.smart_park.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import smartPark.smart_park.exceptions.ResourceNotFoundException;
import smartPark.smart_park.models.entity.OneTimePassword;
import smartPark.smart_park.models.entity.Utilisateur;
import smartPark.smart_park.repository.OneTimePasswordRepository;
import smartPark.smart_park.repository.UtilisateurRepository;
import smartPark.smart_park.services.EmailService;
import smartPark.smart_park.services.OtpService;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OneTimePasswordRepository otpRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmailService emailService;

    @Override
    public void generateAndSendOtp(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        String code = new DecimalFormat("000000").format(new SecureRandom().nextInt(999999));

        OneTimePassword otp = new OneTimePassword();
        otp.setUtilisateur(utilisateur);
        otp.setCode(code);
        otp.setDateExpiration(LocalDateTime.now().plusMinutes(15));
        otpRepository.save(otp);

        emailService.sendOtpEmail(utilisateur.getEmail(), code);
    }

    @Override
    public boolean validateOtp(Long userId, String code) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        Optional<OneTimePassword> otpOpt = otpRepository.findByUtilisateurAndCode(utilisateur, code);

        if (otpOpt.isEmpty() || otpOpt.get().estExpire()) {
            return false;
        }

        otpRepository.delete(otpOpt.get());
        return true;
    }
}