package BC4_Recomendaciones;

import java.util.UUID;
import comun.DatosAuditoria;

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

    // Clean Code: Función pequeña con una sola responsabilidad
    public boolean esDelTipo(String tipo) {
        return this.tipo != null && this.tipo.equalsIgnoreCase(tipo);
    }

    public UUID getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public String getTipo() { return tipo; }
    public DatosAuditoria getAuditoria() { return auditoria; }
}
