package pe.seek.app.customer.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.seek.app.customer.application.port.input.AuthServicePort;
import pe.seek.app.customer.application.port.output.AuthDataPort;
import pe.seek.app.customer.application.port.output.LoadSecurityPort;
import pe.seek.app.customer.domain.TokenDTO;
import pe.seek.app.customer.infrastructure.repository.UserEntity;
import pe.seek.app.shared.exception.UserNotFoundException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
class AuthService implements AuthServicePort {

    private final AuthDataPort authData;
    private final LoadSecurityPort loadSecurity;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenDTO login(String phone, String password) throws UserNotFoundException {
        log.info("Login with phone: {}", phone);
        return Optional.of(authData.findUserByPhone(phone))
                .map(user -> {
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(phone, password)
                    );
                    return user;
                })
                .map(user -> new TokenDTO(loadSecurity.generateToken(user)))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public TokenDTO register(String phone, String password) throws UserNotFoundException {
        log.info("Register with phone: {}", phone);
        UserEntity user = UserEntity.builder()
                .phone(phone)
                .password(passwordEncoder.encode(password))
                .build();
        return Optional.of(authData.saveUser(user))
                .map(u -> new TokenDTO(loadSecurity.generateToken(u)))
                .orElseThrow(UserNotFoundException::new);
    }
}
