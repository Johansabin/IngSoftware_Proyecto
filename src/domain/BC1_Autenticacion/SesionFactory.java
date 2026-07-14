package BC1_Autenticacion;

/**
 * Fabrica encargada de instanciar objetos Sesion de manera controlada.
 */
public class SesionFactory {

    /**
     * Crea una nueva instancia de Sesion inicializada.
     *
     * @param codigoUsuario Codigo del usuario que inicia sesion.
     * @return Una nueva instancia de Sesion.
     */
    public static Sesion registrarSesion(String codigoUsuario) {
        return new Sesion(codigoUsuario);
    }
}