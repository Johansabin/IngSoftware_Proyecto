package comun;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DatosAuditoria {

    private final LocalDateTime creadoEn;
    private final LocalDateTime actualizadoEn;

    // Constructor para registros nuevos
    public DatosAuditoria() {
        this(Clock.system(ZoneOffset.UTC));
    }

    // Constructor con Clock explicito para zona horaria controlada y testabilidad
    public DatosAuditoria(Clock clock) {
        LocalDateTime ahora = LocalDateTime.now(clock);
        this.creadoEn = ahora;
        this.actualizadoEn = ahora;
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
