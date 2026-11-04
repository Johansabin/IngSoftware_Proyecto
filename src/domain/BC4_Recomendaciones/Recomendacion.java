package BC4_Recomendaciones;

import comun.DatosAuditoria;

import java.util.UUID;

public class Recomendacion {

    private final UUID id;
    private String titulo;
    private String contenido;
    private final String tipo;
    private final DatosAuditoria auditoria;

    public Recomendacion(String titulo, String contenido, String tipo) {
        validarTexto(titulo, "El titulo de la recomendacion no puede estar vacio");
        validarTexto(contenido, "El contenido de la recomendacion no puede estar vacio");
        validarTexto(tipo, "El tipo de la recomendacion no puede estar vacio");

        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.contenido = contenido;
        this.tipo = tipo;
        this.auditoria = new DatosAuditoria();
    }

    private static void validarTexto(String valor, String mensajeError) {
        if (valor == null || valor.isBlank()) {
            throw new RecomendacionInvalidaException(mensajeError);
        }
    }
    public boolean esDelTipo(String tipoConsultado) {
        return this.tipo.equalsIgnoreCase(tipoConsultado);
    }
    public void actualizarContenido(String nuevoTitulo, String nuevoContenido) {
        validarTexto(nuevoTitulo, "El titulo de la recomendacion no puede estar vacio");
        validarTexto(nuevoContenido, "El contenido de la recomendacion no puede estar vacio");

        this.titulo = nuevoTitulo;
        this.contenido = nuevoContenido;
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
