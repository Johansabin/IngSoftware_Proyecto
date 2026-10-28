package presentation.BC4_Recomendaciones;

/**
 * Cuerpo JSON recibido al crear una recomendacion.
 * Sigue el patron de {@code StartChatHttpRequest} en BC3.
 */
public class CrearRecomendacionHttpRequest {

    private String titulo;
    private String contenido;
    private String tipo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
