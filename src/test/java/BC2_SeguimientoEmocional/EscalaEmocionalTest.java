package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EscalaEmocionalTest {

    @Test
    void valorFueraDeRango_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new EscalaEmocional(0));
    }

    @Test
    void valorOcho_esAlta() {
        assertTrue(new EscalaEmocional(8).esAlta());
    }
}