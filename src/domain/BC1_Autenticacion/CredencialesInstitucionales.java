package BC1_Autenticacion;

/**
 * Objeto de valor que representa las credenciales de acceso de un estudiante.
 */
public class CredencialesInstitucionales {

    private final String codigoUniversitario;
    private final String hashContrasena;

    /**
     * Constructor con parametros de las credenciales.
     *
     * @param codigoUniversitario Codigo de estudiante.
     * @param hashContrasena Contrasena encriptada o hasheada.
     */
    public CredencialesInstitucionales(String codigoUniversitario, String hashContrasena) {
        if (codigoUniversitario == null || codigoUniversitario.trim().isEmpty()) {
            throw new IllegalArgumentException("El código universitario no puede ser nulo o vacío.");
        }
        if (hashContrasena == null || hashContrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña hash no puede ser nula o vacía.");
        }
        this.codigoUniversitario = codigoUniversitario;
        this.hashContrasena = hashContrasena;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public String getHashContrasena() {
        return hashContrasena;
    }
}