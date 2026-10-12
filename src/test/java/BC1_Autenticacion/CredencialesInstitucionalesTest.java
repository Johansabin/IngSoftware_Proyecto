package BC1_Autenticacion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CredencialesInstitucionalesTest {

    @Test
    void testCredencialesValidas() {
        CredencialesInstitucionales creds = new CredencialesInstitucionales("20214567", "hash123");
        assertEquals("20214567", creds.getCodigoUniversitario());
        assertEquals("hash123", creds.getHashContrasena());
        assertTrue(creds.esCodigoValido());
    }

    @Test
    void testCodigoUniversitarioInvalido() {
        CredencialesInstitucionales creds1 = new CredencialesInstitucionales("123", "hash123");
        assertFalse(creds1.esCodigoValido());

        CredencialesInstitucionales creds2 = new CredencialesInstitucionales("202145678", "hash123");
        assertFalse(creds2.esCodigoValido());

        CredencialesInstitucionales creds3 = new CredencialesInstitucionales("2021ABCD", "hash123");
        assertFalse(creds3.esCodigoValido());
    }

    @Test
    void testConstructorParametrosNulosOVacios() {
        assertThrows(IllegalArgumentException.class, () -> new CredencialesInstitucionales(null, "hash123"));
        assertThrows(IllegalArgumentException.class, () -> new CredencialesInstitucionales("", "hash123"));
        assertThrows(IllegalArgumentException.class, () -> new CredencialesInstitucionales("   ", "hash123"));

        assertThrows(IllegalArgumentException.class, () -> new CredencialesInstitucionales("20214567", null));
        assertThrows(IllegalArgumentException.class, () -> new CredencialesInstitucionales("20214567", ""));
        assertThrows(IllegalArgumentException.class, () -> new CredencialesInstitucionales("20214567", "   "));
    }

    @Test
    void testEqualsAndHashCode() {
        CredencialesInstitucionales c1 = new CredencialesInstitucionales("20214567", "hash123");
        CredencialesInstitucionales c2 = new CredencialesInstitucionales("20214567", "hash123");
        CredencialesInstitucionales c3 = new CredencialesInstitucionales("20219999", "hash123");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
        assertNotEquals(c1, null);
        assertNotEquals(c1, "some string");
        assertEquals(c1, c1);
    }
}
