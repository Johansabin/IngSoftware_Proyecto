package BC2_SeguimientoEmocional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Registro individual de una emocion reportada por un estudiante en una fecha.
 *
 * <p>Estilo de programacion aplicado: <b>Things</b>. Emocion encapsula sus
 * datos (inmutables) y expone comportamiento propio, por ejemplo
 * {@link #indicaEstres()}, en lugar de dejar que otras clases inspeccionen
 * su estado interno y decidan por ella.</p>
 */
public class Emocion {

    private final UUID id;
    private final LocalDate fecha;
    private final String tipo;
    private final EscalaEmocional escala;
    private final String descripcion;

    public Emocion(LocalDate fecha, String tipo, EscalaEmocional escala, String descripcion) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha es obligatoria");
        this.tipo = Objects.requireNonNull(tipo, "El tipo de emocion es obligatorio");
        this.escala = Objects.requireNonNull(escala, "La escala emocional es obligatoria");
        this.descripcion = descripcion;
        this.id = UUID.randomUUID();
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

    /**
     * Una emocion se considera indicadora de estres cuando su escala es alta.
     * La decision la toma la propia capsula, delegando en EscalaEmocional.
     */
    public boolean indicaEstres() {
        return escala.esAlta();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Emocion)) {
            return false;
        }
        Emocion that = (Emocion) obj;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Emocion{id=" + id + ", fecha=" + fecha + ", tipo='" + tipo + "', escala=" + escala + "}";
    }
}
