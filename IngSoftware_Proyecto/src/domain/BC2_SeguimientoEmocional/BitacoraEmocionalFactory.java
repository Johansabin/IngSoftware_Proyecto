package BC2_Seguimiento-Emocional;

import java.util.UUID;

/**
 * Factory del agregado BitacoraEmocional: centraliza la creación de una bitácora
 * vacía para un estudiante nuevo, generando su identificador.
 */
public final class BitacoraEmocionalFactory {

    private BitacoraEmocionalFactory() {
        // Clase de utilidad: no debe instanciarse (evita el code smell "utility class with public constructor").
    }

    /**
     * Crea una bitácora emocional vacía para el estudiante indicado.
     *
     * @param estudianteId identificador del estudiante dueño de la bitácora
     * @return una nueva instancia de BitacoraEmocional
     */
    public static BitacoraEmocional crearParaEstudiante(UUID estudianteId) {
        if (estudianteId == null) {
            throw new IllegalArgumentException("estudianteId no puede ser null");
        }
        return new BitacoraEmocional(UUID.randomUUID(), estudianteId);
    }
}