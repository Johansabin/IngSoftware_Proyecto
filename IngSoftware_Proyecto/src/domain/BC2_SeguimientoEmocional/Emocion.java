package BC2_Seguimiento-Emocional;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidad de dominio: un registro puntual de una emoción reportada por el estudiante.
 * Inmutable después de su creación (no expone setters) para evitar modificaciones
 * inconsistentes una vez registrada en la bitácora.
 */
public final class Emocion {

    private final UUID id;
    private final LocalDate fecha;
    private final String tipo;
    private final EscalaEmocional escala;
    private final String descripcion;

    public Emocion(UUID id, LocalDate fecha, String tipo, EscalaEmocional escala, String descripcion) {
        if (id == null || fecha == null || tipo == null || tipo.isBlank() || escala == null) {
            throw new IllegalArgumentException("id, fecha, tipo y escala son obligatorios para registrar una emoción");
        }
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.escala = escala;
        this.descripcion = descripcion;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public EscalaEmocional getEscala() {
        return escala;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Emocion)) {
            return false;
        }
        Emocion otra = (Emocion) o;
        return id.equals(otra.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}