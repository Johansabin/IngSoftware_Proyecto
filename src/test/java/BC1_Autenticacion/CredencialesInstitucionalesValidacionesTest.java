package BC1_Autenticacion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CredencialesInstitucionalesValidacionesTest {

    @Test
    void codigoConLetrasNoEsValido() {
        CredencialesInstitucionales c=new CredencialesInstitucionales("2021A001","hash");
        boolean resultado=c.esCodigoValido();
        assertFalse(resultado);
    }

    @Test
    void codigoDeSieteDigitosNoEsValido() {
        CredencialesInstitucionales c=new CredencialesInstitucionales("2021000","hash");
        boolean resultado=c.esCodigoValido();
        assertFalse(resultado);
    }

    @Test
    void codigoDeNueveDigitosNoEsValido() {
        CredencialesInstitucionales c=new CredencialesInstitucionales("202100012","hash");
        boolean resultado=c.esCodigoValido();
        assertFalse(resultado);
    }

    @Test
    void codigoDeOchoDigitosEsValido() {
        CredencialesInstitucionales c=new CredencialesInstitucionales("20210001","hash");
        boolean resultado=c.esCodigoValido();
        assertTrue(resultado);
    }

    @Test
    void codigoNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,()->new CredencialesInstitucionales(null,"hash"));
    }

    @Test
    void codigoSoloEspaciosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,()->new CredencialesInstitucionales("   ","hash"));
    }

    @Test
    void hashNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,()->new CredencialesInstitucionales("20210001",null));
    }

    @Test
    void hashSoloEspaciosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,()->new CredencialesInstitucionales("20210001","   "));
    }
}
