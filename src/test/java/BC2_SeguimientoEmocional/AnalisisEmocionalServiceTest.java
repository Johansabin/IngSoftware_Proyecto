package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalisisEmocionalServiceTest {

    private static final LocalDate LUNES = LocalDate.of(2026, 11, 2);

    private final ByteArrayOutputStream salida = new ByteArrayOutputStream();
    private PrintStream salidaOriginal;
    private AnalisisEmocionalService servicio;
    private BitacoraEmocional bitacora;

    @BeforeEach
    void setUp() {
        salidaOriginal = System.out;
        System.setOut(new PrintStream(salida));
        servicio = new AnalisisEmocionalService();
        bitacora = new BitacoraEmocional(UUID.randomUUID());
    }

    @AfterEach
    void tearDown() {
        System.setOut(salidaOriginal);
    }

    private void agregarEstres(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            bitacora.agregarRegistro(
                    new Emocion(LUNES.plusDays(i), "estres", new EscalaEmocional(9), null));
        }
    }

    @Test
    void detectarPatrones_tresRegistrosDelMismoTipo_detectaPatron() {
        // Arrange
        agregarEstres(3);

        // Act
        servicio.detectarPatronesDeEstres(bitacora);

        // Assert
        assertTrue(salida.toString().contains("Patrones de estres detectados en: estres"));
    }

    @Test
    void detectarPatrones_dosRegistros_noDetectaPatron() {
        // Arrange
        agregarEstres(2);

        // Act
        servicio.detectarPatronesDeEstres(bitacora);

        // Assert
        assertTrue(salida.toString().contains("No se detectaron patrones"));
    }

    @Test
    void generarResumen_publicaElResumenSemanal() {
        // Arrange
        agregarEstres(1);

        // Act
        servicio.generarResumen(bitacora, LUNES);

        // Assert
        assertTrue(salida.toString().contains("Resumen semanal"));
    }

    @Test
    void argumentosNulos_lanzanExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> servicio.generarResumen(null, LUNES));
        assertThrows(NullPointerException.class, () -> servicio.generarResumen(bitacora, null));
        assertThrows(NullPointerException.class, () -> servicio.detectarPatronesDeEstres(null));
    }
}
