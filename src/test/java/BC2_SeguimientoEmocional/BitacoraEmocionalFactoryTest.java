package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class BitacoraEmocionalFactoryTest {

    @Test
    void idNulo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class,
                () -> BitacoraEmocionalFactory.crearParaEstudiante(null));
    }

    @Test
    void crea_bitacoraVaciaParaElEstudiante() {
        // Arrange
        UUID estudiante = UUID.randomUUID();

        // Act
        BitacoraEmocional bitacora = BitacoraEmocionalFactory.crearParaEstudiante(estudiante);

        // Assert
        assertEquals(estudiante, bitacora.getEstudianteId());
        assertTrue(bitacora.getRegistros().isEmpty());
    }
}
