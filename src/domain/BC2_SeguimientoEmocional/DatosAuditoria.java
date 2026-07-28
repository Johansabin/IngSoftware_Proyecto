package BC2_SeguimientoEmocional;

import java.time.LocalDateTime;

/**
 * Value Object de auditoría: registra cuándo se creó y cuándo se modificó por
 * última vez un agregado de dominio. No existía en los stubs originales pero
 * era referenciado por BitacoraEmocional, así que se agrega aquí.
 *
 * <p>Nota de correccion: el paquete declarado originalmente
 * ({@code BC2_Seguimiento-Emocional}) contenia un guion, caracter invalido en
 * un identificador de paquete Java, por lo que no compilaba. Se corrigio a
 * {@code BC2_SeguimientoEmocional} para que coincida con el resto de clases.
 * Ademas, el metodo se nombro {@code marcarModificacion()} en vez de
 * {@code actualizar()} porque asi es invocado desde
 * {@link BitacoraEmocional#agregarRegistro}, y el nombre revela mejor la
 * intencion (registrar que el agregado fue modificado) que un verbo generico.</p>
 */
public final class DatosAuditoria {

    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public DatosAuditoria() {
        LocalDateTime ahora = LocalDateTime.now();
        this.fechaCreacion = ahora;
        this.fechaActualizacion = ahora;
    }

    /** Marca esta bitacora como modificada, actualizando la fecha al momento actual. */
    public void marcarModificacion() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
}
