package pe.seek.app.shared.exception;

import static pe.seek.app.shared.constants.GenericErrors.GEN_ALL_02;

public class CreateCustomerFallBackException extends GeneralEntityException {

    public CreateCustomerFallBackException() {
        super(GEN_ALL_02);
    }
}
