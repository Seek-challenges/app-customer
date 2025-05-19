package pe.seek.app.customer.application.port.output;

import pe.seek.app.customer.infrastructure.repository.UserEntity;
import pe.seek.app.shared.exception.UserNotFoundException;

public interface AuthDataPort {
    UserEntity saveUser(UserEntity user);
    UserEntity findUserByPhone(String phone) throws UserNotFoundException;
}
