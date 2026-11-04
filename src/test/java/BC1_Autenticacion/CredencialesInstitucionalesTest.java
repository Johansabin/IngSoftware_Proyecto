package BC1_Autenticacion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias de Dominio - CredencialesInstitucionales (Invariantes)")
class CredencialesInstitucionalesTest {

    @Test
    @DisplayName("Debe crear credenciales válidas con código y hash correctos")
    void crearCredencialesValidas() {
        // Arrange
        String codigo = "20214567";
        String hash = "hashSecret123";

        // Act
        CredencialesInstitucionales creds = new CredencialesInstitucionales(codigo, hash);

        // Assert
        assertNotNull(creds);
        assertEquals(codigo, creds.getCodigoUniversitario());
        assertEquals(hash, creds.getHashContrasena());
        assertTrue(creds.esCodigoValido());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el código universitario es nulo o vacío")
    void lanzarExcepcionCuandoCodigoInvalido() {
        // Arrange
        String hash = "hashSecret123";

        // Act & Assert
        IllegalArgumentException exNull = assertThrows(IllegalArgumentException.class, () -> {
            new CredencialesInstitucionales(null, hash);
        });
        assertEquals("El código universitario no puede estar vacío.", exNull.getMessage());

        IllegalArgumentException exEmpty = assertThrows(IllegalArgumentException.class, () -> {
            new CredencialesInstitucionales("", hash);
        });
        assertEquals("El código universitario no puede estar vacío.", exEmpty.getMessage());

        IllegalArgumentException exBlank = assertThrows(IllegalArgumentException.class, () -> {
            new CredencialesInstitucionales("   ", hash);
        });
        assertEquals("El código universitario no puede estar vacío.", exBlank.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el hash de contraseña es nulo o vacío")
    void lanzarExcepcionCuandoHashInvalido() {
        // Arrange
        String codigo = "20214567";

        // Act & Assert
        IllegalArgumentException exNull = assertThrows(IllegalArgumentException.class, () -> {
            new CredencialesInstitucionales(codigo, null);
        });
        assertEquals("La contraseña hash no puede estar vacía.", exNull.getMessage());

        IllegalArgumentException exEmpty = assertThrows(IllegalArgumentException.class, () -> {
            new CredencialesInstitucionales(codigo, "");
        });
        assertEquals("La contraseña hash no puede estar vacía.", exEmpty.getMessage());

        IllegalArgumentException exBlank = assertThrows(IllegalArgumentException.class, () -> {
            new CredencialesInstitucionales(codigo, "   ");
        });
        assertEquals("La contraseña hash no puede estar vacía.", exBlank.getMessage());
    }

    @Test
    @DisplayName("Debe validar formato de código de 8 dígitos numéricos")
    void validarFormatoCodigoUniversitario() {
        // Arrange
        CredencialesInstitucionales valido = new CredencialesInstitucionales("12345678", "hash");
        CredencialesInstitucionales corto = new CredencialesInstitucionales("1234567", "hash");
        CredencialesInstitucionales largo = new CredencialesInstitucionales("123456789", "hash");
        CredencialesInstitucionales conLetras = new CredencialesInstitucionales("2021ABCD", "hash");

        // Act & Assert
        assertTrue(valido.esCodigoValido());
        assertFalse(corto.esCodigoValido());
        assertFalse(largo.esCodigoValido());
        assertFalse(conLetras.esCodigoValido());
    }

    @Test
    @DisplayName("Debe validar igualdad y código hash según valor")
    void validarEqualsYHashCode() {
        // Arrange
        CredencialesInstitucionales c1 = new CredencialesInstitucionales("20214567", "hash123");
        CredencialesInstitucionales c2 = new CredencialesInstitucionales("20214567", "hash123");
        CredencialesInstitucionales c3 = new CredencialesInstitucionales("20219999", "hash123");

        // Act & Assert
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
        assertNotEquals(c1, null);
        assertNotEquals(c1, "otroTipo");
    }
}
