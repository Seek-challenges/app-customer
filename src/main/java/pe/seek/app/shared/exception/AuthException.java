package pe.seek.app.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {
    private final HttpStatus statusCode;
    private final String message;

    public AuthException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public AuthException() {
        super("Authentication failed");
        this.statusCode = HttpStatus.UNAUTHORIZED;
        this.message = "Authentication failed";
    }
}
