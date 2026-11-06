package BC6_PrivacidadSeguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Issue #10 - Capa de Dominio del Modulo 6 (Privacidad y Seguridad).
 *
 * <pre>
 * Clase/Metodo  | Caso de prueba            | Valores de prueba | Resultado esperado
 * --------------+---------------------------+-------------------+------------------------------
 * constructor   | Estudiante nulo           | null              | NullPointerException
 * constructor   | Consentimiento aceptado   | true              | isAceptado() == true
 * constructor   | Consentimiento rechazado  | false             | isAceptado() == false
 * constructor   | Datos generados           | -                 | id no nulo, fecha de hoy
 * constructor   | Identidad unica           | dos instancias    | ids distintos
 * </pre>
 */
class ConsentimientoTest {

    private UUID estudianteId;

    @BeforeEach
    void setUp() {
        // Arrange (comun)
        estudianteId = UUID.randomUUID();
    }

    @Test
    @DisplayName("constructor: rechaza un estudiante nulo")
    void constructor_estudianteNulo_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> new Consentimiento(null, true));
    }

    @Test
    @DisplayName("constructor: conserva el estudiante y el consentimiento aceptado")
    void constructor_aceptado_conservaLosDatos() {
        // Arrange (en setUp)

        // Act
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);

        // Assert
        assertEquals(estudianteId, consentimiento.getEstudianteId());
        assertTrue(consentimiento.isAceptado());
    }

    @Test
    @DisplayName("constructor: conserva el consentimiento rechazado")
    void constructor_rechazado_conservaLosDatos() {
        // Arrange (en setUp)

        // Act
        Consentimiento consentimiento = new Consentimiento(estudianteId, false);

        // Assert
        assertFalse(consentimiento.isAceptado());
    }

    @Test
    @DisplayName("constructor: genera id y fecha de aceptacion")
    void constructor_generaIdYFecha() {
        // Arrange
        LocalDate antes = LocalDate.now();

        // Act
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);

        // Assert
        assertNotNull(consentimiento.getId());
        assertFalse(consentimiento.getFechaAceptacion().isBefore(antes));
    }

    @Test
    @DisplayName("constructor: cada consentimiento tiene un id distinto")
    void constructor_dosInstancias_tienenIdsDistintos() {
        // Arrange (en setUp)

        // Act
        Consentimiento primero = new Consentimiento(estudianteId, true);
        Consentimiento segundo = new Consentimiento(estudianteId, true);

        // Assert
        assertNotEquals(primero.getId(), segundo.getId());
    }
}
