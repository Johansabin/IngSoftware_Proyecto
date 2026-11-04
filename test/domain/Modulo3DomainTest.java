package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Modulo3DomainTest {

    @Test
    void testCrearEntidadConNombreVacioLanzaExcepcion() {
        // Arrange & Act & Assert
        // Verifica que no se permitan datos nulos o vacíos (invariante)
        assertThrows(IllegalArgumentException.class, () -> {
            new EntidadModulo3("");
        }, "El nombre de la entidad del Módulo 3 no puede estar vacío.");
    }

    @Test
    void testCrearEntidadConMontoInvalidoLanzaExcepcion() {
        // Arrange & Act & Assert
        // Verifica que los valores numéricos cumplan sus restricciones
        assertThrows(IllegalArgumentException.class, () -> {
            new EntidadModulo3("Prueba", -10);
        }, "El valor numérico debe ser mayor a cero.");
    }
}
