package presentation.BC1_Autenticacion;

import java.util.UUID;

public class LogoutRequest {
    private UUID sessionId;
    private String codigoUniversitario;

    public LogoutRequest() {}

    public LogoutRequest(UUID sessionId, String codigoUniversitario) {
        this.sessionId = sessionId;
        this.codigoUniversitario = codigoUniversitario;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public String getCodigoUniversitario() {
        return codigoUniversitario;
    }

    public void setCodigoUniversitario(String codigoUniversitario) {
        this.codigoUniversitario = codigoUniversitario;
    }
}
