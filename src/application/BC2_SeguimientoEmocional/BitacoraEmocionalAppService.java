package application.BC2_SeguimientoEmocional;

import BC2_SeguimientoEmocional.Emocion;
import BC2_SeguimientoEmocional.ResumenSemanal;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Casos de uso del bounded context de Seguimiento Emocional.
 * Orquesta el dominio sin contener reglas de negocio.
 */
public interface BitacoraEmocionalAppService {

    /**
     * Registra una emocion en la bitacora del estudiante; la crea si aun no existe.
     */
    void registrarEmocion(UUID estudianteId, Emocion emocion);

    /**
     * Calcula y devuelve el resumen de la semana que contiene la fecha indicada.
     *
     * @throws BitacoraNotFoundException si el estudiante no tiene bitacora
     */
    ResumenSemanal obtenerResumenSemanal(UUID estudianteId, LocalDate semana);

    /**
     * Devuelve todos los registros emocionales del estudiante (vacio si no tiene bitacora).
     */
    List<Emocion> obtenerHistorial(UUID estudianteId);
}
