package smartPark.smart_park.services;

public interface OtpService {
    void generateAndSendOtp(Long userId);
    boolean validateOtp(Long userId, String code);
}