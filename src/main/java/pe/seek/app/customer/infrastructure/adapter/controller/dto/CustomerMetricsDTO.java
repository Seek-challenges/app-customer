package pe.seek.app.customer.infrastructure.adapter.controller.dto;

public record CustomerMetricsDTO(
        Double averageAge,
        Double averageBirthDate,
        Double ageStandardDeviation
) {
}
