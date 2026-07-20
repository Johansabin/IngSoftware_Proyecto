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
        if (codigoUniversitario == null || codigoUniversitario.isBlank()) {
            throw new IllegalArgumentException("El código universitario no puede estar vacío.");
        }
        if (hashContrasena == null || hashContrasena.isBlank()) {
            throw new IllegalArgumentException("La contraseña hash no puede estar vacía.");
        }
        this.codigoUniversitario = codigoUniversitario;
        this.hashContrasena = hashContrasena;
    }

    /**
     * Valida si el código universitario cumple con el formato requerido.
     *
     * @return true si es válido, de lo contrario false.
     */
    public boolean esCodigoValido() {
        return this.codigoUniversitario.matches("^\\d{8}$");
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public String getHashContrasena() {
        return hashContrasena;
    }
}