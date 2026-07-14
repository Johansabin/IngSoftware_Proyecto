package BC2_Seguimiento-Emocional;

import java.time.LocalDateTime;

/**
 * Value Object de auditoría: registra cuándo se creó y cuándo se modificó por
 * última vez un agregado de dominio. No existía en los stubs originales pero
 * era referenciado por BitacoraEmocional, así que se agrega aquí.
 */
public final class DatosAuditoria {

    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public DatosAuditoria() {
        LocalDateTime ahora = LocalDateTime.now();
        this.fechaCreacion = ahora;
        this.fechaActualizacion = ahora;
    }

    /** Actualiza la marca de última modificación al momento actual. */
    public void actualizar() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
}
