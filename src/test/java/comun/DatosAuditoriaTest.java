package comun;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatosAuditoriaTest {

    @Test
    void constructorPorDefectoInicializaFechas() {
        DatosAuditoria auditoria = new DatosAuditoria();

        assertNotNull(auditoria.getCreadoEn());
        assertNotNull(auditoria.getActualizadoEn());
        assertEquals(auditoria.getCreadoEn(), auditoria.getActualizadoEn());
    }

    @Test
    void constructorConClockUsaZonaHorariaExplicita() {
        Instant instanteFijo = Instant.parse("2026-11-18T15:30:00Z");
        Clock clockFijo = Clock.fixed(instanteFijo, ZoneOffset.UTC);

        DatosAuditoria auditoria = new DatosAuditoria(clockFijo);

        LocalDateTime esperado = LocalDateTime.of(2026, 11, 18, 15, 30, 0);
        assertEquals(esperado, auditoria.getCreadoEn());
        assertEquals(esperado, auditoria.getActualizadoEn());
    }

    @Test
    void constructorRehidratacionConservaFechasDadas() {
        LocalDateTime creado = LocalDateTime.of(2026, 10, 1, 9, 0);
        LocalDateTime actualizado = LocalDateTime.of(2026, 10, 2, 14, 15);

        DatosAuditoria auditoria = new DatosAuditoria(creado, actualizado);

        assertEquals(creado, auditoria.getCreadoEn());
        assertEquals(actualizado, auditoria.getActualizadoEn());
    }
}
