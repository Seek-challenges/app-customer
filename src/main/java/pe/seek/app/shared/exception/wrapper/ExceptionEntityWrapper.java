package pe.seek.app.shared.exception.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pe.seek.app.shared.constants.GenericErrors;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionEntityWrapper {
    private String entity;
    private String message;
    private String code;
    private String title;
    private List<FieldValidationWrapper> errors;

    public ExceptionEntityWrapper buildFromError(MethodArgumentNotValidException methodArgumentNotValidException) {
        ExceptionEntityWrapper entityValidationWrapper = new ExceptionEntityWrapper();
        entityValidationWrapper.setEntity(methodArgumentNotValidException.getBindingResult().getObjectName());
        entityValidationWrapper.setMessage(GenericErrors.GEN_ALL_01.getMessage());
        entityValidationWrapper.setCode(GenericErrors.GEN_ALL_01.name());
        List<FieldValidationWrapper> listErrors = new ArrayList<>();

        for (ObjectError objectError : methodArgumentNotValidException.getBindingResult().getAllErrors()) {
            String field = objectError instanceof FieldError ? ((FieldError) objectError).getField() : objectError.getObjectName();
            listErrors.add(new FieldValidationWrapper(field, objectError.getDefaultMessage()));
        }

        entityValidationWrapper.setErrors(listErrors);
        return entityValidationWrapper;
    }
}
