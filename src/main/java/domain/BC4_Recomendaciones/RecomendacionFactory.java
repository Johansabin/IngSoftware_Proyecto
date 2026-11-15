package BC4_Recomendaciones;

/**
 * Fabrica del agregado {@link Recomendacion}, equivalente a
 * {@code ChatSessionFactory} en BC3.
 *
 * <p>Centraliza la creacion de recomendaciones para que las capas superiores no
 * dependan directamente del constructor del agregado.</p>
 */
public final class RecomendacionFactory {

    private static final String TIPO_ACTIVIDAD = "ACTIVIDAD";
    private static final String TIPO_CONSEJO = "CONSEJO";

    private RecomendacionFactory() {
        // Clase de utilidades: no debe instanciarse.
    }

    /**
     * Crea una recomendacion del tipo indicado.
     *
     * @param titulo    titulo de la recomendacion
     * @param contenido contenido de la recomendacion
     * @param tipo      categoria de la recomendacion
     * @return la recomendacion creada
     */
    public static Recomendacion crear(String titulo, String contenido, String tipo) {
        return new Recomendacion(titulo, contenido, tipo);
    }

    /**
     * Crea una actividad sugerida (historia HF.3.2).
     *
     * @param titulo    titulo de la actividad
     * @param contenido descripcion de la actividad
     * @return la actividad creada
     */
    public static Recomendacion crearActividad(String titulo, String contenido) {
        return new Recomendacion(titulo, contenido, TIPO_ACTIVIDAD);
    }

    /**
     * Crea un consejo de bienestar (historia HF.3.1).
     *
     * @param titulo    titulo del consejo
     * @param contenido contenido del consejo
     * @return el consejo creado
     */
    public static Recomendacion crearConsejo(String titulo, String contenido) {
        return new Recomendacion(titulo, contenido, TIPO_CONSEJO);
    }
}
