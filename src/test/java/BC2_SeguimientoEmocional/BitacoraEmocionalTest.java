package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BitacoraEmocionalTest {

    // 2026-11-02 es lunes
    private static final LocalDate LUNES = LocalDate.of(2026, 11, 2);

    private BitacoraEmocional bitacora;

    @BeforeEach
    void setUp() {
        bitacora = new BitacoraEmocional(UUID.randomUUID());
    }

    private static Emocion emocion(LocalDate fecha, String tipo, int valor) {
        return new Emocion(fecha, tipo, new EscalaEmocional(valor), null);
    }

    @Test
    void estudianteNulo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> new BitacoraEmocional(null));
    }

    @Test
    void agregarRegistroNulo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> bitacora.agregarRegistro(null));
    }

    @Test
    void agregarRegistro_loAnadeYActualizaAuditoria() {
        // Arrange
        Emocion e = emocion(LUNES, "estres", 8);

        // Act
        bitacora.agregarRegistro(e);

        // Assert
        assertEquals(1, bitacora.getRegistros().size());
        assertFalse(bitacora.getAuditoria().getFechaActualizacion()
                .isBefore(bitacora.getAuditoria().getFechaCreacion()));
    }

    @Test
    void getRegistros_noPermiteModificarLaLista() {
        // Arrange
        Emocion e = emocion(LUNES, "calma", 2);

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> bitacora.getRegistros().add(e));
    }

    @Test
    void resumenSemanal_calculaPromedioYDiasDePaz() {
        // Arrange: 8 (estres), 4 y 2 (sin estres)
        bitacora.agregarRegistro(emocion(LUNES, "estres", 8));
        bitacora.agregarRegistro(emocion(LUNES.plusDays(1), "ansiedad", 4));
        bitacora.agregarRegistro(emocion(LUNES.plusDays(2), "calma", 2));

        // Act
        bitacora.calcularResumenSemanal(LUNES);
        ResumenSemanal resumen = bitacora.obtenerResumen(LUNES);

        // Assert
        assertNotNull(resumen);
        assertEquals(14.0 / 3, resumen.getPromedioEstres(), 0.001);
        assertEquals(2, resumen.getDiasDePaz());
        assertEquals(LUNES, resumen.getSemanaInicio());
    }

    @Test
    void resumenSemanal_ignoraRegistrosDeOtraSemana() {
        // Arrange
        bitacora.agregarRegistro(emocion(LUNES, "calma", 2));
        bitacora.agregarRegistro(emocion(LUNES.plusDays(7), "estres", 10));

        // Act
        bitacora.calcularResumenSemanal(LUNES);
        ResumenSemanal resumen = bitacora.obtenerResumen(LUNES);

        // Assert
        assertEquals(2.0, resumen.getPromedioEstres(), 0.001);
        assertEquals(1, resumen.getDiasDePaz());
    }

    @Test
    void resumenSemanal_sinRegistros_devuelveCeros() {
        // Arrange (bitacora vacia)

        // Act
        bitacora.calcularResumenSemanal(LUNES);
        ResumenSemanal resumen = bitacora.obtenerResumen(LUNES);

        // Assert
        assertEquals(0.0, resumen.getPromedioEstres(), 0.001);
        assertEquals(0, resumen.getDiasDePaz());
    }

    @Test
    void obtenerResumen_aceptaCualquierDiaDeLaSemana() {
        // Arrange
        bitacora.agregarRegistro(emocion(LUNES, "calma", 3));
        bitacora.calcularResumenSemanal(LUNES.plusDays(4)); // viernes

        // Act
        ResumenSemanal resumen = bitacora.obtenerResumen(LUNES.plusDays(2)); // miercoles

        // Assert
        assertNotNull(resumen);
        assertEquals(LUNES, resumen.getSemanaInicio());
    }

    @Test
    void obtenerResumen_sinCalcular_devuelveNull() {
        // Arrange & Act & Assert
        assertNull(bitacora.obtenerResumen(LUNES));
    }

    @Test
    void calcularResumenSemanal_semanaNula_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> bitacora.calcularResumenSemanal(null));
    }
}
