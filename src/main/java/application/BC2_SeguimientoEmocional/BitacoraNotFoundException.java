package application.BC2_SeguimientoEmocional;

import java.util.UUID;

/**
 * Indica que el estudiante no tiene una bitacora emocional registrada.
 * Equivalente a {@code RecomendacionNotFoundException} en BC4.
 */
public class BitacoraNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BitacoraNotFoundException(UUID estudianteId) {
        super("No existe una bitacora emocional para el estudiante " + estudianteId);
    }
}
