package pe.seek.app.customer.infrastructure.adapter.broker.events;

import lombok.Builder;

@Builder
public record CreatedCustomerEvent(
        String firstName,
        String lastName,
        String phone,
        String age,
        String birthDate
) {
}
