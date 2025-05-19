package pe.seek.app.customer.infrastructure.adapter.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank
        String phone,
        @NotBlank
        String password
) {
}
