package smartPark.smart_park.exceptions; // Adaptez le package

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyReportException extends RuntimeException {
    public EmptyReportException(String message) {
        super(message);
    }
}