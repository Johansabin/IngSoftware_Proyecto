package BC1_Autenticacion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias de Dominio - Sesion y SesionFactory (Invariantes)")
class SesionTest {

    @Test
    @DisplayName("Debe inicializar sesión activa con identificador y fecha de inicio")
    void crearSesionValida() {
        // Arrange
        String codigoUsuario = "20214567";
        LocalDateTime antesDeCrear = LocalDateTime.now().minusSeconds(1);

        // Act
        Sesion sesion = new Sesion(codigoUsuario);

        // Assert
        assertNotNull(sesion.getId());
        assertEquals(codigoUsuario, sesion.getCodigoUsuario());
        assertTrue(sesion.isActiva());
        assertNotNull(sesion.getFechaInicio());
        assertTrue(sesion.getFechaInicio().isAfter(antesDeCrear));
        assertNull(sesion.getFechaFin());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el código de usuario es nulo o vacío al instanciar Sesion")
    void lanzarExcepcionCuandoCodigoUsuarioInvalido() {
        // Arrange & Act & Assert
        IllegalArgumentException exNull = assertThrows(IllegalArgumentException.class, () -> {
            new Sesion(null);
        });
        assertEquals("El código de usuario no puede ser nulo o vacío.", exNull.getMessage());

        IllegalArgumentException exEmpty = assertThrows(IllegalArgumentException.class, () -> {
            new Sesion("");
        });
        assertEquals("El código de usuario no puede ser nulo o vacío.", exEmpty.getMessage());

        IllegalArgumentException exBlank = assertThrows(IllegalArgumentException.class, () -> {
            new Sesion("   ");
        });
        assertEquals("El código de usuario no puede ser nulo o vacío.", exBlank.getMessage());
    }

    @Test
    @DisplayName("Debe desactivar la sesión y registrar la fecha de fin correctamente")
    void desactivarSesion() {
        // Arrange
        Sesion sesion = new Sesion("20214567");
        assertTrue(sesion.isActiva());
        assertNull(sesion.getFechaFin());

        // Act
        sesion.desactivar();

        // Assert
        assertFalse(sesion.isActiva());
        assertNotNull(sesion.getFechaFin());
        assertFalse(sesion.getFechaFin().isBefore(sesion.getFechaInicio()));
    }

    @Test
    @DisplayName("Debe verificar igualdad basada en UUID de la sesión")
    void validarEqualsYHashCode() {
        // Arrange
        Sesion s1 = new Sesion("20214567");
        Sesion s2 = new Sesion("20214567");

        // Act & Assert
        assertEquals(s1, s1);
        assertNotEquals(s1, s2);
        assertNotEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1, null);
        assertNotEquals(s1, "otroObjeto");
    }

    @Test
    @DisplayName("SesionFactory debe registrar sesión válida y activa")
    void sesionFactoryRegistrarSesion() {
        // Arrange
        String codigoUsuario = "20219999";

        // Act
        Sesion sesion = SesionFactory.registrarSesion(codigoUsuario);

        // Assert
        assertNotNull(sesion);
        assertEquals(codigoUsuario, sesion.getCodigoUsuario());
        assertTrue(sesion.isActiva());
    }
}
