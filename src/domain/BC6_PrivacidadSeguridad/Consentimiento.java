package BC6_PrivacidadSeguridad;

import java.io.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.*;

/**
 * 
 */
public class Consentimiento {

    /**
     * Default constructor
     */
    public Consentimiento() {
        this(UUID.randomUUID(), true);
    }

    public Consentimiento(UUID estudianteId, boolean aceptado) {
        this.id = UUID.randomUUID();
        this.estudianteId = Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        this.fechaAceptacion = LocalDate.now();
        this.aceptado = aceptado;
    }

    /**
     * 
     */
    private UUID id;

    /**
     * 
     */
    private UUID estudianteId;

    /**
     * 
     */
    private LocalDate fechaAceptacion;

    /**
     * 
     */
    private boolean aceptado;

    public UUID getId() {
        return id;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public LocalDate getFechaAceptacion() {
        return fechaAceptacion;
    }

    public boolean isAceptado() {
        return aceptado;
    }

}
