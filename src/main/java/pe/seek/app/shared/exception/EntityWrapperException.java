package pe.seek.app.shared.exception;
import lombok.Getter;
import pe.seek.app.shared.constants.GenericErrors;

@Getter
public class EntityWrapperException extends Exception {

    private final int statusCode;
    private final String errorBody;

    public EntityWrapperException(int statusCode, String errorBody) {
        this.statusCode = statusCode;
        this.errorBody = errorBody;
    }

    public EntityWrapperException(GenericErrors genericError) {
        this.statusCode = genericError.getStatusCode();
        this.errorBody = genericError.getMessage();
    }
}
