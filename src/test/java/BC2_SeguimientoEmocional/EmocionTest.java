package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EmocionTest {

    private static final LocalDate FECHA = LocalDate.of(2026, 11, 2);

    @Test
    void fechaNula_lanzaExcepcion() {
        // Arrange
        EscalaEmocional escala = new EscalaEmocional(5);

        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> new Emocion(null, "estres", escala, "x"));
    }

    @Test
    void tipoNulo_lanzaExcepcion() {
        // Arrange
        EscalaEmocional escala = new EscalaEmocional(5);

        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> new Emocion(FECHA, null, escala, "x"));
    }

    @Test
    void tipoEnBlanco_lanzaExcepcion() {
        // Arrange
        EscalaEmocional escala = new EscalaEmocional(5);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> new Emocion(FECHA, "   ", escala, "x"));
    }

    @Test
    void escalaNula_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class,
                () -> new Emocion(FECHA, "estres", null, "x"));
    }

    @Test
    void indicaEstresSegunEscala() {
        // Arrange
        Emocion alta = new Emocion(FECHA, "estres", new EscalaEmocional(9), null);
        Emocion baja = new Emocion(FECHA, "calma", new EscalaEmocional(2), null);

        // Act & Assert
        assertTrue(alta.indicaEstres());
        assertFalse(baja.indicaEstres());
    }

    @Test
    void getValorEscalaDelegaEnLaEscala() {
        // Arrange
        Emocion emocion = new Emocion(FECHA, "ansiedad", new EscalaEmocional(6), "desc");

        // Act
        int valor = emocion.getValorEscala();

        // Assert
        assertEquals(6, valor);
    }
}
