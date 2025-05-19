package pe.seek.app.customer.infrastructure.adapter.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pe.seek.app.customer.application.port.input.AuthServicePort;
import pe.seek.app.customer.domain.TokenDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.api.AuthApi;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.LoginDTO;
import pe.seek.app.shared.exception.UserNotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
class AutController implements AuthApi {

    private final AuthServicePort authService;

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws UserNotFoundException {
        return authService.login(
                loginDTO.phone(),
                loginDTO.password()
        );
    }
}
