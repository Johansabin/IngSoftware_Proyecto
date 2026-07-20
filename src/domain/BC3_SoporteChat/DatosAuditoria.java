package BC3_SoporteChat;

import java.time.LocalDateTime;

public class DatosAuditoria {

    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public DatosAuditoria() {
        LocalDateTime ahora = LocalDateTime.now();
        this.fechaCreacion = ahora;
        this.fechaActualizacion = ahora;
    }

    public void registrarActualizacion() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
}
