package smartPark.smart_park.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Exception levée lorsqu'un token JWT est invalide ou expiré
 */
public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}