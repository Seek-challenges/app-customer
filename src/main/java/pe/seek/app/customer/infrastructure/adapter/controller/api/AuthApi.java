package pe.seek.app.customer.infrastructure.adapter.controller.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.seek.app.customer.domain.TokenDTO;
import pe.seek.app.customer.infrastructure.adapter.controller.dto.LoginDTO;
import pe.seek.app.shared.exception.UserNotFoundException;

@RequestMapping("/v1/auth")
public interface AuthApi {
    @PostMapping
    TokenDTO login(
            @RequestBody @Valid LoginDTO loginDTO
    ) throws UserNotFoundException;
}
