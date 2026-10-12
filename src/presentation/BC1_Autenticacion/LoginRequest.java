package presentation.BC1_Autenticacion;

public class LoginRequest {
    private String codigoUniversitario;
    private String contrasena;
    private String ipOrigen;

    public LoginRequest() {}

    public LoginRequest(String codigoUniversitario, String contrasena, String ipOrigen) {
        this.codigoUniversitario = codigoUniversitario;
        this.contrasena = contrasena;
        this.ipOrigen = ipOrigen;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public void setIpOrigen(String ipOrigen) {
        this.ipOrigen = ipOrigen;
    }
}
