package BC6_PrivacidadSeguridad;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DatosAuditoria {

    private static final ZoneId ZONA = ZoneId.of("America/Lima");

    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public DatosAuditoria() {
        LocalDateTime ahora = LocalDateTime.now(ZONA);
        this.fechaCreacion = ahora;
        this.fechaActualizacion = ahora;
    }

    public void registrarActualizacion() {
        this.fechaActualizacion = LocalDateTime.now(ZONA);
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
}
