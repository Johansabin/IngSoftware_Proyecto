package BC6_PrivacidadSeguridad;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Consentimiento {

    private final UUID id;
    private final UUID estudianteId;
    private final LocalDate fechaAceptacion;
    private final boolean aceptado;

    public Consentimiento(UUID estudianteId, boolean aceptado) {
        this.id = UUID.randomUUID();
        this.estudianteId = Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        this.fechaAceptacion = LocalDate.now();
        this.aceptado = aceptado;
    }

    public UUID getId() { return id; }
    public UUID getEstudianteId() { return estudianteId; }
    public LocalDate getFechaAceptacion() { return fechaAceptacion; }
    public boolean isAceptado() { return aceptado; }
}
