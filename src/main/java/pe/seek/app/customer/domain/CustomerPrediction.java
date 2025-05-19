package pe.seek.app.customer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CustomerPrediction extends Customer {
    private Double lifeExpectancy;
    private LocalDate predictedDeathDate;
}
