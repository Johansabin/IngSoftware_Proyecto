package BC4_Recomendaciones;

/**
 * Excepcion de dominio que indica que una {@link Recomendacion} no cumple sus
 * invariantes.
 *
 * <p>Estilo de programacion: <b>Error/Exception Handling</b>. La validacion se
 * concentra en el punto de construccion y se comunica con una excepcion
 * especifica del dominio, en lugar de devolver {@code null} o codigos de error.
 * Asi, cualquier {@code Recomendacion} que exista en el sistema es valida por
 * construccion.</p>
 */
public class RecomendacionInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecomendacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
