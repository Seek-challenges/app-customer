package pe.seek.app.shared.exception;

import lombok.Getter;
import pe.seek.app.shared.constants.GenericErrors;

@Getter
public class GeneralException extends GeneralEntityException {
    public GeneralException(GenericErrors genericError) {
        super(genericError);
    }
}
