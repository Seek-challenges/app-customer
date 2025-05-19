package pe.seek.app.config.web;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.seek.app.shared.constants.GenericErrors;
import pe.seek.app.shared.exception.AuthException;
import pe.seek.app.shared.exception.EntityWrapperException;
import pe.seek.app.shared.exception.GeneralEntityException;
import pe.seek.app.shared.exception.wrapper.ExceptionEntityWrapper;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionEntityWrapper> handleInternalServerError(Exception ex) {
        log.error("Exception handled as Internal server error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionEntityWrapper.builder()
                        .code(GenericErrors.GEN_ALL_01.name())
                        .message(GenericErrors.GEN_ALL_01.getMessage())
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionEntityWrapper> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionEntityWrapper.builder().build().buildFromError(ex));
    }

    @ExceptionHandler({EntityWrapperException.class})
    public ResponseEntity<?> handleEntityWrapperException(@NotNull EntityWrapperException ex) {
        return ResponseEntity
                .status(HttpStatus.valueOf(ex.getStatusCode())).contentType(MediaType.APPLICATION_JSON).body(ex.getErrorBody());
    }

    @ExceptionHandler({GeneralEntityException.class})
    public ResponseEntity<ExceptionEntityWrapper> handleGeneralUnprocessableEntity(GeneralEntityException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ExceptionEntityWrapper.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build());

    }

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ExceptionEntityWrapper> handleAuthException(AuthException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ExceptionEntityWrapper.builder()
                .code("401")
                .message(ex.getMessage())
                .build());
    }
}
