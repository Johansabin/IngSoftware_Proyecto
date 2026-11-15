package BC2_SeguimientoEmocional;

import java.util.Objects;
import java.util.UUID;

/**
 * Fabrica responsable de construir bitacoras emocionales validas para un estudiante.
 *
 * <p><b>Nota sobre la firma:</b> en el esqueleto original el metodo era
 * {@code void crearParaEstudiante(UUID)}. Un metodo de fabrica sin valor de
 * retorno no puede cumplir su proposito (el objeto creado se perderia), asi
 * que se ajusto el tipo de retorno a {@code BitacoraEmocional}. No se cambio
 * el nombre de la clase, el paquete ni la firma de entrada.</p>
 *
 * <p>Esta clase no implementa ninguno de los 4 estilos evaluados en el
 * informe; se mantiene con codigo limpio (validacion temprana / fail-fast)
 * para cumplir buenas practicas de SonarLint.</p>
 */
public final class BitacoraEmocionalFactory {

    private BitacoraEmocionalFactory() {
        // Clase utilitaria: no debe instanciarse.
    }

    /**
     * Crea una nueva bitacora emocional, vacia, para el estudiante indicado.
     *
     * @param estudianteId identificador del estudiante, obligatorio
     * @return una bitacora emocional recien creada asociada al estudiante
     * @throws NullPointerException si {@code estudianteId} es nulo
     */
    public static BitacoraEmocional crearParaEstudiante(UUID estudianteId) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        return new BitacoraEmocional(estudianteId);
    }
}
