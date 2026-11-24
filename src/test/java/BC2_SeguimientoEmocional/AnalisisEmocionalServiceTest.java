package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class AnalisisEmocionalServiceTest {

    private static final LocalDate LUNES = LocalDate.of(2026, 11, 2);

    private Logger logger;
    private ListAppender<ILoggingEvent> logs;
    private AnalisisEmocionalService servicio;
    private BitacoraEmocional bitacora;

    @BeforeEach
    void setUp() {
        logger = (Logger) LoggerFactory.getLogger(AnalisisEmocionalService.class);
        logger.setLevel(Level.INFO);
        logs = new ListAppender<>();
        logs.start();
        logger.addAppender(logs);
        servicio = new AnalisisEmocionalService();
        bitacora = new BitacoraEmocional(UUID.randomUUID());
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(logs);
        logs.stop();
    }

    private void agregarEstres(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            bitacora.agregarRegistro(
                    new Emocion(LUNES.plusDays(i), "estres", new EscalaEmocional(9), null));
        }
    }

    private List<String> mensajesRegistrados() {
        return logs.list.stream().map(ILoggingEvent::getFormattedMessage).toList();
    }

    @Test
    void detectarPatrones_tresRegistrosDelMismoTipo_devuelveYRegistraPatrones() {
        // Arrange
        agregarEstres(3);

        // Act
        List<String> patrones = servicio.detectarPatronesDeEstres(bitacora);

        // Assert
        assertEquals(List.of("estres"), patrones);
        assertEquals(
                List.of("Se detectaron 1 patron(es) de estres sostenido"), mensajesRegistrados());
    }

    @Test
    void detectarPatrones_dosRegistros_devuelveListaVacia() {
        // Arrange
        agregarEstres(2);

        // Act
        List<String> patrones = servicio.detectarPatronesDeEstres(bitacora);

        // Assert
        assertTrue(patrones.isEmpty());
        assertEquals(
                List.of("No se detectaron patrones de estres sostenido"), mensajesRegistrados());
    }

    @Test
    void generarResumen_devuelveResumenYRegistraMensajeGenericoSinDatosEmocionales() {
        // Arrange
        agregarEstres(1);

        // Act
        ResumenSemanal resumen = servicio.generarResumen(bitacora, LUNES);

        // Assert: devuelve el resultado calculado y el log no expone emociones del estudiante
        assertEquals(9.0, resumen.getPromedioEstres(), 0.001);
        assertEquals(0, resumen.getDiasDePaz());
        assertEquals(LUNES, resumen.getSemanaInicio());
        assertEquals(List.of("Resumen semanal generado"), mensajesRegistrados());
    }

    @Test
    void argumentosNulos_lanzanExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> servicio.generarResumen(null, LUNES));
        assertThrows(NullPointerException.class, () -> servicio.generarResumen(bitacora, null));
        assertThrows(NullPointerException.class, () -> servicio.detectarPatronesDeEstres(null));
    }
}
