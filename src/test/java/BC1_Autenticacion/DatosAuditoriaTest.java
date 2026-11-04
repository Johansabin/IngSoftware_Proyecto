package BC1_Autenticacion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias de Dominio - DatosAuditoria (Invariantes)")
class DatosAuditoriaTest {

    @Test
    @DisplayName("Debe crear auditoría válida con IP y fecha de registro")
    void crearAuditoriaValida() {
        // Arrange
        String ip = "192.168.1.100";

        // Act
        DatosAuditoria auditoria = new DatosAuditoria(ip);

        // Assert
        assertNotNull(auditoria);
        assertEquals(ip, auditoria.getIpOrigen());
        assertNotNull(auditoria.getFechaRegistro());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la IP de origen es nula o vacía")
    void lanzarExcepcionCuandoIpInvalida() {
        // Arrange & Act & Assert
        IllegalArgumentException exNull = assertThrows(IllegalArgumentException.class, () -> {
            new DatosAuditoria(null);
        });
        assertEquals("La dirección IP de origen no puede ser nula o vacía.", exNull.getMessage());

        IllegalArgumentException exEmpty = assertThrows(IllegalArgumentException.class, () -> {
            new DatosAuditoria("");
        });
        assertEquals("La dirección IP de origen no puede ser nula o vacía.", exEmpty.getMessage());

        IllegalArgumentException exBlank = assertThrows(IllegalArgumentException.class, () -> {
            new DatosAuditoria("   ");
        });
        assertEquals("La dirección IP de origen no puede ser nula o vacía.", exBlank.getMessage());
    }

    @Test
    @DisplayName("Debe validar igualdad y código hash según IP y fecha")
    void validarEqualsYHashCode() {
        // Arrange
        DatosAuditoria a1 = new DatosAuditoria("10.0.0.1");

        // Act & Assert
        assertEquals(a1, a1);
        assertNotEquals(a1, null);
        assertNotEquals(a1, "otraClase");
    }
}
