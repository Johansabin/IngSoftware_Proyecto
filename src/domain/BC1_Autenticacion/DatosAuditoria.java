package BC1_Autenticacion;

import java.time.LocalDateTime;

/**
 * Objeto de valor para el registro de auditoria de los inicios de sesion.
 */
public class DatosAuditoria {

    private final String ipOrigen;
    private final LocalDateTime fechaRegistro;

    /**
     * Constructor que registra los detalles de auditoria.
     *
     * @param ipOrigen Direccion IP del dispositivo.
     */
    public DatosAuditoria(String ipOrigen) {
        if (ipOrigen == null || ipOrigen.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección IP de origen no puede ser nula o vacía.");
        }
        this.ipOrigen = ipOrigen;
        this.fechaRegistro = LocalDateTime.now();
    }

    public String getIpOrigen() {
        return ipOrigen;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}