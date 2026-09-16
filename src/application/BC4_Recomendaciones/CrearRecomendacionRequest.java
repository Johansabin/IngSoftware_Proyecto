package application.BC4_Recomendaciones;

/**
 * DTO de entrada para el caso de uso de creacion de recomendaciones.
 * Sigue el patron de {@code StartChatRequest} en BC3.
 */
public class CrearRecomendacionRequest {

    private final String titulo;
    private final String contenido;
    private final String tipo;

    public CrearRecomendacionRequest(String titulo, String contenido, String tipo) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.tipo = tipo;
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
}
