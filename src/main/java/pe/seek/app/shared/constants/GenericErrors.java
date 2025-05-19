package pe.seek.app.shared.constants;

import lombok.Getter;

@Getter
public enum GenericErrors {
    GEN_ALL_01(500,"En este momento no estamos disponibles - Espera unos minutos para continuar."),
    GEN_ALL_02(503, "En este momento no pudimos registrar tu solicitud, pero hemos guardado tus datos y serás notificado tan pronto completemos el proceso. Gracias por tu paciencia.");

    private final int statusCode;
    private final String message;

    GenericErrors(int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
