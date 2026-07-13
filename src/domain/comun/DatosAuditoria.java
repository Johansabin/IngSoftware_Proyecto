package comun;

import java.time.LocalDateTime;

/**
 * Value Object compartido entre bounded contexts.
 * Vive en un paquete común porque varios BC lo necesitan
 * (BC1, BC2, BC3, BC4, BC6) — no le pertenece a ninguno en particular.
 */
public class DatosAuditoria {

    private final LocalDateTime creadoEn;
    private final LocalDateTime actualizadoEn;

    public DatosAuditoria() {
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }
}
