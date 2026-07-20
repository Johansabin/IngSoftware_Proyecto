package BC3_SoporteChat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Mensaje {

    private static final int LONGITUD_MAXIMA_CONTENIDO = 1000;

    private final UUID id;
    private final String contenido;
    private final RolRemitente remitenteRol;
    private final LocalDateTime timestamp;

    public Mensaje(String contenido, RolRemitente remitenteRol) {
        this(UUID.randomUUID(), contenido, remitenteRol, LocalDateTime.now());
    }

    public Mensaje(UUID id, String contenido, RolRemitente remitenteRol, LocalDateTime timestamp) {
        this.id = Objects.requireNonNull(id, "El id del mensaje es obligatorio");
        this.contenido = validarContenido(contenido);
        this.remitenteRol = Objects.requireNonNull(remitenteRol, "El rol del remitente es obligatorio");
        this.timestamp = Objects.requireNonNull(timestamp, "La fecha del mensaje es obligatoria");
    }

    private String validarContenido(String contenido) {
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del mensaje no puede estar vacio");
        }

        String contenidoNormalizado = contenido.trim();
        if (contenidoNormalizado.length() > LONGITUD_MAXIMA_CONTENIDO) {
            throw new IllegalArgumentException("El contenido del mensaje supera la longitud permitida");
        }

        return contenidoNormalizado;
    }

    public UUID getId() {
        return id;
    }

    public String getContenido() {
        return contenido;
    }

    public RolRemitente getRemitenteRol() {
        return remitenteRol;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
