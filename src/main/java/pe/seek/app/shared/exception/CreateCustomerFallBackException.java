package pe.seek.app.shared.exception;

import pe.seek.app.shared.constants.GenericErrors;

public class CreateCustomerFallBackException extends GeneralEntityException {

    public CreateCustomerFallBackException(GenericErrors genericError) {
        super(genericError);
    }
}
