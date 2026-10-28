package presentation.BC4_Recomendaciones;

import BC4_Recomendaciones.Recomendacion;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representacion HTTP de una recomendacion.
 * Sigue el patron de {@code ChatSessionResponse} en BC3.
 */
public class RecomendacionResponse {

    private final UUID id;
    private final String titulo;
    private final String contenido;
    private final String tipo;
    private final LocalDateTime creadoEn;

    public RecomendacionResponse(Recomendacion recomendacion) {
        this.id = recomendacion.getId();
        this.titulo = recomendacion.getTitulo();
        this.contenido = recomendacion.getContenido();
        this.tipo = recomendacion.getTipo();
        this.creadoEn = recomendacion.getAuditoria().getCreadoEn();
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

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }
}
