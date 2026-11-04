package BC5_Notificaciones;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root del bounded context de Notificaciones.
 * Representa un recordatorio programado para un estudiante.
 */
public class Recordatorio {

    private final UUID id;
    private final UUID estudianteId;
    private final String mensaje;
    private final LocalDateTime fechaProgramada;
    private boolean activo;

    public Recordatorio(UUID estudianteId, String mensaje, LocalDateTime fechaProgramada) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser nulo o vacío");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudianteId no puede ser nulo");
        }
        if (fechaProgramada == null) {
            throw new IllegalArgumentException("La fecha programada no puede ser nula");
        }
        this.id = UUID.randomUUID();
        this.estudianteId = estudianteId;
        this.mensaje = mensaje;
        this.fechaProgramada = fechaProgramada;
        this.activo = true;
    }

    /**
     * Desactiva el recordatorio para que deje de notificarse.
     */
    public void desactivar() {
        this.activo = false;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaProgramada() {
        return fechaProgramada;
    }

    public boolean isActivo() {
        return activo;
    }
}
