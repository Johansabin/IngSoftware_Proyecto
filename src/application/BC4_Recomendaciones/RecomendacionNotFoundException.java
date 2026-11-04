package application.BC4_Recomendaciones;

import java.util.UUID;

/**
 * Indica que no existe una recomendacion con el identificador solicitado.
 * Equivalente a {@code ChatSessionNotFoundException} en BC3.
 */
public class RecomendacionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecomendacionNotFoundException(UUID id) {
        super("No existe una recomendacion con id " + id);
    }
}
