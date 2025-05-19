package pe.seek.app.customer.infrastructure.adapter.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AppCustomerRequestDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        String phone,
        @NotBlank
        String age,
        @NotNull
        LocalDate birthDate,
        @NotBlank
        String password
) {
}
