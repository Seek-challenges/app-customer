package pe.seek.app.customer.infrastructure.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.seek.app.customer.application.port.output.AuthDataPort;
import pe.seek.app.customer.infrastructure.repository.AuthRepository;
import pe.seek.app.customer.infrastructure.repository.UserEntity;
import pe.seek.app.shared.exception.UserNotFoundException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthPersistenceAdapter implements AuthDataPort {

    private final AuthRepository authRepository;

    @Override
    public UserEntity saveUser(UserEntity user) {
        log.info("Saving user: {}", user.getUsername());
        return authRepository.save(user);
    }

    @Override
    public UserEntity findUserByPhone(String phone) throws UserNotFoundException {
        log.info("Finding user by phone: {}", phone);
        return authRepository.findByPhone(phone)
                .orElseThrow(UserNotFoundException::new);
    }
}
