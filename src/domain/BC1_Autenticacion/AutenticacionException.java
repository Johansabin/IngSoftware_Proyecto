package BC1_Autenticacion;

/**
 * Excepción de dominio para controlar errores explícitos durante la autenticación.
 */
public class AutenticacionException extends Exception {

    private static final long serialVersionUID = 1L;

    public AutenticacionException(String mensaje) {
        super(mensaje);
    }
}