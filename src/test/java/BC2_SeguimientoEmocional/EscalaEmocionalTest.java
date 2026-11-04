package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EscalaEmocionalTest {

    @Test
    void valorBajoElMinimo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new EscalaEmocional(0));
    }

    @Test
    void valorSobreElMaximo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new EscalaEmocional(11));
    }

    @Test
    void categoriaEnLosLimites() {
        // Arrange & Act & Assert
        assertEquals("BAJO", new EscalaEmocional(1).getCategoria());
        assertEquals("BAJO", new EscalaEmocional(3).getCategoria());
        assertEquals("MEDIO", new EscalaEmocional(4).getCategoria());
        assertEquals("MEDIO", new EscalaEmocional(7).getCategoria());
        assertEquals("ALTO", new EscalaEmocional(8).getCategoria());
        assertEquals("ALTO", new EscalaEmocional(10).getCategoria());
    }

    @Test
    void esAltaSoloDesdeOcho() {
        // Arrange
        EscalaEmocional siete = new EscalaEmocional(7);
        EscalaEmocional ocho = new EscalaEmocional(8);

        // Act & Assert
        assertFalse(siete.esAlta());
        assertTrue(ocho.esAlta());
    }

    @Test
    void igualdadPorValor() {
        // Arrange
        EscalaEmocional a = new EscalaEmocional(5);
        EscalaEmocional b = new EscalaEmocional(5);

        // Act & Assert
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
