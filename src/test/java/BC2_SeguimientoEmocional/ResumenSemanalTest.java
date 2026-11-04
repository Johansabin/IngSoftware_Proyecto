package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ResumenSemanalTest {

    private static final LocalDate LUNES = LocalDate.of(2026, 11, 2);

    @Test
    void promedioNegativo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ResumenSemanal(-1, 0, LUNES));
    }

    @Test
    void diasDePazNegativos_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new ResumenSemanal(1, -1, LUNES));
    }

    @Test
    void semanaNula_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> new ResumenSemanal(1, 1, null));
    }

    @Test
    void igualdadSoloPorSemanaInicio() {
        // Arrange
        ResumenSemanal a = new ResumenSemanal(2.0, 1, LUNES);
        ResumenSemanal b = new ResumenSemanal(9.0, 5, LUNES);

        // Act & Assert
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
