package BC4_Recomendaciones;

import java.util.UUID;
import comun.DatosAuditoria;

/**
 * Aggregate Root del bounded context de Recomendaciones.
 */
public class Recomendacion {

    private final UUID id;
    private final String titulo;
    private final String contenido;
    private final String tipo;
    private final DatosAuditoria auditoria;

    public Recomendacion(String titulo, String contenido, String tipo) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.contenido = contenido;
        this.tipo = tipo;
        this.auditoria = new DatosAuditoria();
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public DatosAuditoria getAuditoria() {
        return auditoria;
    }
}
