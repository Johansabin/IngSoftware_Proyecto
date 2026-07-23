package BC2_SeguimientoEmocional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de persistencia para bitacoras emocionales.
 *
 * <p><b>Nota sobre la firma:</b> en el esqueleto original
 * {@code buscarPorEstudiante} y {@code buscarPorRangoFecha} retornaban
 * {@code void}. Un metodo de busqueda sin valor de retorno no puede cumplir
 * su proposito, asi que se ajustaron sus tipos de retorno a
 * {@code Optional<BitacoraEmocional>} y {@code List<Emocion>}
 * respectivamente. Los nombres de metodo y los parametros de entrada no
 * cambiaron.</p>
 */
public interface BitacoraEmocionalRepository {

    /**
     * @param bitacora bitacora a persistir
     */
    void guardar(BitacoraEmocional bitacora);

    /**
     * @param estudianteId identificador del estudiante
     * @return la bitacora del estudiante, si existe
     */
    Optional<BitacoraEmocional> buscarPorEstudiante(UUID estudianteId);

    /**
     * @param estudianteId identificador del estudiante
     * @param desde fecha inicial del rango, inclusive
     * @param hasta fecha final del rango, inclusive
     * @return los registros emocionales del estudiante dentro del rango indicado
     */
    List<Emocion> buscarPorRangoFecha(UUID estudianteId, LocalDate desde, LocalDate hasta);
}
