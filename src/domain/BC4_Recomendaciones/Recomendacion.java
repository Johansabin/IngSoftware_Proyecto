package BC4_Recomendaciones;

import java.util.Objects;
import java.util.UUID;

/**
 * 
 */
public class Recomendacion {

    /**
     * Default constructor
     */
    public Recomendacion() {
        this("Respiracion consciente", "Realizar una pausa breve para respirar y reducir tension.", "Bienestar");
    }

    public Recomendacion(String titulo, String contenido, String tipo) {
        this.id = UUID.randomUUID();
        this.titulo = Objects.requireNonNull(titulo, "El titulo de la recomendacion es obligatorio");
        this.contenido = Objects.requireNonNull(contenido, "El contenido de la recomendacion es obligatorio");
        this.tipo = Objects.requireNonNull(tipo, "El tipo de recomendacion es obligatorio");
        this.auditoria = new DatosAuditoria();
    }

    /**
     * 
     */
    private UUID id;

    /**
     * 
     */
    private String titulo;

    /**
     * 
     */
    private String contenido;

    /**
     * 
     */
    private String tipo;

    /**
     * 
     */
    private DatosAuditoria auditoria;

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
