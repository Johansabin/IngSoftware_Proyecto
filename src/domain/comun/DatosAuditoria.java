package comun;

import java.time.LocalDateTime;

public class DatosAuditoria {

    private final LocalDateTime creadoEn;
    private final LocalDateTime actualizadoEn;

    // Constructor para registros nuevos
    public DatosAuditoria() {
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    // Constructor para rehidratación (Clean Code: Claridad de inicialización)
    public DatosAuditoria(LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }
}
