package pe.seek.app.customer.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthCredential {
    private String phone;
    private String password;
}
