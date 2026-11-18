package BC1_Autenticacion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class CredencialesInstitucionalesValidacionesTest {

    @ParameterizedTest
    @ValueSource(strings={"2021A001","2021000","202100012"})
    void codigoConFormatoInvalidoNoEsValido(String codigo) {
        CredencialesInstitucionales c=new CredencialesInstitucionales(codigo,"hash");
        boolean resultado=c.esCodigoValido();
        assertFalse(resultado);
    }

    @Test
    void codigoDeOchoDigitosEsValido() {
        CredencialesInstitucionales c=new CredencialesInstitucionales("20210001","hash");
        boolean resultado=c.esCodigoValido();
        assertTrue(resultado);
    }

    @ParameterizedTest
    @CsvSource(value={"NULL,hash","'   ',hash","20210001,NULL","20210001,'   '"},nullValues="NULL")
    void datosNulosOVaciosLanzanExcepcion(String codigo,String hash) {
        assertThrows(IllegalArgumentException.class,()->new CredencialesInstitucionales(codigo,hash));
    }
}
