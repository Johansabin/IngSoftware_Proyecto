package BC6_PrivacidadSeguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Issue #10 - Capa de Dominio del Modulo 6: auditoria de la politica.
 *
 * <pre>
 * Clase/Metodo           | Caso de prueba           | Valores de prueba | Resultado esperado
 * -----------------------+--------------------------+-------------------+------------------------------------
 * constructor            | Fechas iniciales         | -                 | creacion == actualizacion, no nulas
 * registrarActualizacion | Actualizar               | una llamada       | actualizacion no anterior a creacion
 * registrarActualizacion | Creacion inmutable       | una llamada       | fechaCreacion no cambia
 * </pre>
 */
class DatosAuditoriaTest {

    @Test
    @DisplayName("constructor: creacion y actualizacion parten con la misma fecha")
    void constructor_fechasIniciales_soloIguales() {
        // Arrange & Act
        DatosAuditoria auditoria = new DatosAuditoria();

        // Assert
        assertNotNull(auditoria.getFechaCreacion());
        assertEquals(auditoria.getFechaCreacion(), auditoria.getFechaActualizacion());
    }

    @Test
    @DisplayName("registrarActualizacion: la actualizacion no es anterior a la creacion")
    void registrarActualizacion_actualizaLaFecha() {
        // Arrange
        DatosAuditoria auditoria = new DatosAuditoria();

        // Act
        auditoria.registrarActualizacion();

        // Assert
        assertFalse(auditoria.getFechaActualizacion().isBefore(auditoria.getFechaCreacion()));
    }

    @Test
    @DisplayName("registrarActualizacion: no modifica la fecha de creacion")
    void registrarActualizacion_noCambiaLaCreacion() {
        // Arrange
        DatosAuditoria auditoria = new DatosAuditoria();
        LocalDateTime creacion = auditoria.getFechaCreacion();

        // Act
        auditoria.registrarActualizacion();

        // Assert
        assertEquals(creacion, auditoria.getFechaCreacion());
    }
}
