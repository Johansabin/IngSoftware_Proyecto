package BC6_PrivacidadSeguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Issue #10 - Capa de Dominio del Modulo 6: invariantes y reglas de
 * {@link PoliticaPrivacidad}.
 *
 * <pre>
 * Clase/Metodo          | Caso de prueba                  | Valores de prueba          | Resultado esperado
 * ----------------------+---------------------------------+----------------------------+--------------------------------
 * constructor           | Version nula/vacia/en blanco    | null, "", " ", tab         | IllegalArgumentException
 * constructor           | Contenido nulo/vacio/en blanco  | null, "", " ", tab         | IllegalArgumentException
 * constructor           | Datos validos                   | "v1.0", "texto"            | id, auditoria, sin consentimientos
 * constructor           | Normaliza espacios              | "  v1.0  ", "  texto  "    | valores recortados
 * agregarConsentimiento | Consentimiento nulo             | null                       | NullPointerException
 * agregarConsentimiento | Agregar uno valido              | consentimiento aceptado    | queda registrado
 * agregarConsentimiento | Registra auditoria              | un consentimiento          | actualizacion no anterior a creacion
 * getConsentimientos    | Lista no modificable            | add() externo              | UnsupportedOperationException
 * estaAceptadaPor       | Estudiante que acepto           | aceptado = true            | true
 * estaAceptadaPor       | Estudiante que rechazo          | aceptado = false           | false
 * estaAceptadaPor       | Sin consentimiento              | otro estudiante            | false
 * estaAceptadaPor       | Rechazo y luego aceptacion      | false, luego true          | true
 * estaAceptadaPor       | Id nulo                         | null                       | NullPointerException
 * </pre>
 */
class PoliticaPrivacidadTest {

    private PoliticaPrivacidad politica;
    private UUID estudianteId;

    @BeforeEach
    void setUp() {
        // Arrange (comun)
        politica = new PoliticaPrivacidad("v1.0", "Tratamiento de datos de salud mental");
        estudianteId = UUID.randomUUID();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    @DisplayName("constructor: rechaza una version nula, vacia o en blanco")
    void constructor_versionInvalida_lanzaExcepcion(String version) {
        // Arrange (valor recibido por parametro)

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PoliticaPrivacidad(version, "texto"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    @DisplayName("constructor: rechaza un contenido nulo, vacio o en blanco")
    void constructor_contenidoInvalido_lanzaExcepcion(String contenido) {
        // Arrange (valor recibido por parametro)

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new PoliticaPrivacidad("v1.0", contenido));
    }

    @Test
    @DisplayName("constructor: crea una politica valida sin consentimientos")
    void constructor_datosValidos_creaPoliticaVacia() {
        // Arrange (en setUp)

        // Act (en setUp)

        // Assert
        assertNotNull(politica.getId());
        assertNotNull(politica.getAuditoria());
        assertEquals("v1.0", politica.getVersion());
        assertTrue(politica.getConsentimientos().isEmpty());
    }

    @Test
    @DisplayName("constructor: recorta los espacios de version y contenido")
    void constructor_espaciosAlrededor_losRecorta() {
        // Arrange & Act
        PoliticaPrivacidad recortada = new PoliticaPrivacidad("  v2.0  ", "  texto  ");

        // Assert
        assertEquals("v2.0", recortada.getVersion());
        assertEquals("texto", recortada.getContenido());
    }

    @Test
    @DisplayName("agregarConsentimiento: rechaza un consentimiento nulo")
    void agregarConsentimiento_nulo_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> politica.agregarConsentimiento(null));
    }

    @Test
    @DisplayName("agregarConsentimiento: registra el consentimiento")
    void agregarConsentimiento_valido_quedaRegistrado() {
        // Arrange
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);

        // Act
        politica.agregarConsentimiento(consentimiento);

        // Assert
        assertEquals(1, politica.getConsentimientos().size());
        assertTrue(politica.getConsentimientos().contains(consentimiento));
    }

    @Test
    @DisplayName("agregarConsentimiento: actualiza los datos de auditoria")
    void agregarConsentimiento_registraAuditoria() {
        // Arrange
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);

        // Act
        politica.agregarConsentimiento(consentimiento);

        // Assert
        DatosAuditoria auditoria = politica.getAuditoria();
        assertFalse(auditoria.getFechaActualizacion().isBefore(auditoria.getFechaCreacion()));
    }

    @Test
    @DisplayName("getConsentimientos: la lista devuelta no se puede modificar")
    void getConsentimientos_listaNoModificable() {
        // Arrange
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);

        // Act & Assert
        assertThrows(UnsupportedOperationException.class,
                () -> politica.getConsentimientos().add(consentimiento));
    }

    @Test
    @DisplayName("estaAceptadaPor: true si el estudiante acepto")
    void estaAceptadaPor_estudianteAcepto_devuelveTrue() {
        // Arrange
        politica.agregarConsentimiento(new Consentimiento(estudianteId, true));

        // Act
        boolean aceptada = politica.estaAceptadaPor(estudianteId);

        // Assert
        assertTrue(aceptada);
    }

    @Test
    @DisplayName("estaAceptadaPor: false si el estudiante rechazo")
    void estaAceptadaPor_estudianteRechazo_devuelveFalse() {
        // Arrange
        politica.agregarConsentimiento(new Consentimiento(estudianteId, false));

        // Act
        boolean aceptada = politica.estaAceptadaPor(estudianteId);

        // Assert
        assertFalse(aceptada);
    }

    @Test
    @DisplayName("estaAceptadaPor: false si el estudiante no tiene consentimiento")
    void estaAceptadaPor_sinConsentimiento_devuelveFalse() {
        // Arrange
        politica.agregarConsentimiento(new Consentimiento(UUID.randomUUID(), true));

        // Act
        boolean aceptada = politica.estaAceptadaPor(estudianteId);

        // Assert
        assertFalse(aceptada);
    }

    @Test
    @DisplayName("estaAceptadaPor: true si rechazo primero y acepto despues")
    void estaAceptadaPor_rechazoYLuegoAceptacion_devuelveTrue() {
        // Arrange
        politica.agregarConsentimiento(new Consentimiento(estudianteId, false));
        politica.agregarConsentimiento(new Consentimiento(estudianteId, true));

        // Act
        boolean aceptada = politica.estaAceptadaPor(estudianteId);

        // Assert
        assertTrue(aceptada);
    }

    @Test
    @DisplayName("estaAceptadaPor: rechaza un id nulo")
    void estaAceptadaPor_idNulo_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> politica.estaAceptadaPor(null));
    }
}
