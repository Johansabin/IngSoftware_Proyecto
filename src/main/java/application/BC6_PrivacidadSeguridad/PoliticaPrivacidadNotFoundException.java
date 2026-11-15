package application.BC6_PrivacidadSeguridad;

/**
 * Indica que no hay ninguna politica de privacidad vigente registrada.
 * Equivalente a {@code BitacoraNotFoundException} en BC2.
 */
public class PoliticaPrivacidadNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PoliticaPrivacidadNotFoundException() {
        super("No existe una politica de privacidad vigente");
    }
}
