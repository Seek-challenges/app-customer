package pe.seek.app.customer.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
public class Customer {
    private String firstName;
    private String lastName;
    private String phone;
    private String age;
    private LocalDate birthDate;
}
