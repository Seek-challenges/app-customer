package pe.seek.app.customer.infrastructure.adapter.controller.dto;

import java.time.LocalDate;

public record AppCustomerRequestDTO(
        String firstName,
        String lastName,
        String phone,
        String age,
        LocalDate birthDate
) {
}
