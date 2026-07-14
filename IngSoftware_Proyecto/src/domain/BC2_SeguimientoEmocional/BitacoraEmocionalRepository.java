package BC2_Seguimiento-Emocional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Contrato de persistencia del agregado BitacoraEmocional. La implementación
 * concreta (JPA, archivo, memoria, etc.) vive en la capa de infraestructura.
 */
public interface BitacoraEmocionalRepository {

    /**
     * Persiste (crea o actualiza) la bitácora emocional dada.
     *
     * @param bitacora bitácora a guardar
     */
    void guardar(BitacoraEmocional bitacora);

    /**
     * Busca la bitácora emocional de un estudiante.
     *
     * @param estudianteId identificador del estudiante
     * @return la bitácora encontrada, o null si el estudiante no tiene una
     */
    BitacoraEmocional buscarPorEstudiante(UUID estudianteId);

    /**
     * Busca los registros emocionales de un estudiante dentro de un rango de fechas.
     *
     * @param estudianteId identificador del estudiante
     * @param desde        fecha inicial del rango (inclusive)
     * @param hasta        fecha final del rango (inclusive)
     * @return lista de emociones registradas en ese rango; vacía si no hay ninguna
     */
    List<Emocion> buscarPorRangoFecha(UUID estudianteId, LocalDate desde, LocalDate hasta);
}
