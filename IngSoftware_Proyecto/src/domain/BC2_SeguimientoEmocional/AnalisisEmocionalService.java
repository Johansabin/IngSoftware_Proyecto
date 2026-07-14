package BC2_Seguimiento-Emocional;

import java.time.LocalDate;

/**
 * Servicio de dominio para análisis emocional que involucra lógica que no
 * pertenece naturalmente a una sola entidad (cruza reglas de negocio propias
 * del bounded context, no del agregado en sí).
 */
public final class AnalisisEmocionalService {

    private static final int UMBRAL_REGISTROS_ALTOS_PARA_PATRON = 3;
    private static final String CATEGORIA_ALTO_ESTRES = "ALTO";

    /**
     * Genera el resumen semanal de una bitácora para la semana indicada.
     *
     * @param bitacora bitácora a analizar; no puede ser null
     * @param semana   fecha de inicio de la semana a analizar; no puede ser null
     * @return el resumen semanal calculado
     */
    public ResumenSemanal generarResumen(BitacoraEmocional bitacora, LocalDate semana) {
        if (bitacora == null || semana == null) {
            throw new IllegalArgumentException("bitacora y semana son obligatorios");
        }
        return bitacora.calcularResumenSemanal(semana);
    }

    /**
     * Detecta si la bitácora muestra un patrón de estrés: al menos
     * {@value #UMBRAL_REGISTROS_ALTOS_PARA_PATRON} registros de categoría ALTO
     * en todo su historial.
     *
     * @param bitacora bitácora a analizar; no puede ser null
     * @return true si se detecta un patrón de estrés, false en caso contrario
     */
    public boolean detectarPatronesDeEstres(BitacoraEmocional bitacora) {
        if (bitacora == null) {
            throw new IllegalArgumentException("bitacora es obligatoria");
        }
        long registrosDeAltoEstres = bitacora.getRegistros().stream()
                .filter(registro -> CATEGORIA_ALTO_ESTRES.equals(registro.getEscala().getCategoria()))
                .count();
        return registrosDeAltoEstres >= UMBRAL_REGISTROS_ALTOS_PARA_PATRON;
    }
}