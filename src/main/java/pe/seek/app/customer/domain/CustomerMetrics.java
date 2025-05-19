package pe.seek.app.customer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerMetrics {
    private Double averageAge;
    private Double averageBirthDate;
    private Double ageStandardDeviation;
}
