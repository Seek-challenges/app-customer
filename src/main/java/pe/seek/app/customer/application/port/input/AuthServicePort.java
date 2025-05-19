package pe.seek.app.customer.application.port.input;

import pe.seek.app.customer.domain.TokenDTO;
import pe.seek.app.shared.exception.UserNotFoundException;

public interface AuthServicePort {
    TokenDTO login(String phone, String password) throws UserNotFoundException;
    TokenDTO register(String phone, String password) throws UserNotFoundException;
}
