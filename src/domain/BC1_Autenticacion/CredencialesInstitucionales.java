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