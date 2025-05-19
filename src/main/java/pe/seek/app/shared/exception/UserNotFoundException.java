package pe.seek.app.shared.exception;

import static pe.seek.app.shared.constants.GenericErrors.GEN_ALL_03;

public class UserNotFoundException extends GeneralEntityException{
    public UserNotFoundException() {
        super(GEN_ALL_03);
    }
}
