package application.BC6_PrivacidadSeguridad;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import BC6_PrivacidadSeguridad.Consentimiento;
import BC6_PrivacidadSeguridad.PoliticaPrivacidad;
import BC6_PrivacidadSeguridad.PoliticaPrivacidadRepository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Issue #10 - Capa de Servicio de Aplicacion del Modulo 6 con Mocks. El
 * repositorio se simula con Mockito: no se toca ninguna persistencia real.
 *
 * <pre>
 * Clase/Metodo            | Caso de prueba                 | Valores de prueba            | Resultado esperado
 * ------------------------+--------------------------------+------------------------------+----------------------------------------
 * constructor             | Repositorio nulo               | null                         | NullPointerException
 * obtenerPoliticaVigente  | Hay politica vigente           | repo devuelve politica       | devuelve esa politica
 * obtenerPoliticaVigente  | No hay politica vigente        | repo devuelve vacio          | PoliticaPrivacidadNotFoundException
 * registrarConsentimiento | Consentimiento aceptado        | politica vigente             | se agrega y guardar(politica)
 * registrarConsentimiento | Consentimiento rechazado       | politica vigente             | se registra; estaAceptadaPor == false
 * registrarConsentimiento | Sin politica vigente           | repo devuelve vacio          | NotFoundException, sin guardar
 * registrarConsentimiento | Parametros nulos               | null en id o consentimiento  | NullPointerException, sin tocar repo
 * registrarConsentimiento | Consentimiento de otro alumno  | ids distintos                | IllegalArgumentException, sin tocar repo
 * </pre>
 */
@ExtendWith(MockitoExtension.class)
class PoliticaPrivacidadAppServiceImplTest {

    @Mock
    private PoliticaPrivacidadRepository repositorioMock;

    private PoliticaPrivacidadAppServiceImpl servicio;
    private PoliticaPrivacidad politica;
    private UUID estudianteId;

    @BeforeEach
    void setUp() {
        // Arrange (comun)
        servicio = new PoliticaPrivacidadAppServiceImpl(repositorioMock);
        politica = new PoliticaPrivacidad("v1.0", "Tratamiento de datos de salud mental");
        estudianteId = UUID.randomUUID();
    }

    @Test
    @DisplayName("constructor: rechaza un repositorio nulo")
    void constructor_repositorioNulo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> new PoliticaPrivacidadAppServiceImpl(null));
    }

    // ---------------------------------------------------- obtenerPoliticaVigente

    @Test
    @DisplayName("obtenerPoliticaVigente: devuelve la politica vigente del repositorio")
    void obtenerPoliticaVigente_existe_laDevuelve() {
        // Arrange
        when(repositorioMock.buscarVigente()).thenReturn(Optional.of(politica));

        // Act
        PoliticaPrivacidad vigente = servicio.obtenerPoliticaVigente();

        // Assert
        assertSame(politica, vigente);
    }

    @Test
    @DisplayName("obtenerPoliticaVigente: lanza excepcion si no hay politica vigente")
    void obtenerPoliticaVigente_noExiste_lanzaNotFound() {
        // Arrange
        when(repositorioMock.buscarVigente()).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PoliticaPrivacidadNotFoundException.class, () -> servicio.obtenerPoliticaVigente());
    }

    // -------------------------------------------------- registrarConsentimiento

    @Test
    @DisplayName("registrarConsentimiento: agrega el consentimiento aceptado y guarda la politica")
    void registrarConsentimiento_aceptado_agregaYGuarda() {
        // Arrange
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);
        when(repositorioMock.buscarVigente()).thenReturn(Optional.of(politica));

        // Act
        servicio.registrarConsentimiento(estudianteId, consentimiento);

        // Assert
        assertTrue(politica.getConsentimientos().contains(consentimiento));
        assertTrue(politica.estaAceptadaPor(estudianteId));
        verify(repositorioMock).guardar(politica);
    }

    @Test
    @DisplayName("registrarConsentimiento: registra tambien un rechazo")
    void registrarConsentimiento_rechazado_quedaRegistradoSinAceptar() {
        // Arrange
        Consentimiento rechazo = new Consentimiento(estudianteId, false);
        when(repositorioMock.buscarVigente()).thenReturn(Optional.of(politica));

        // Act
        servicio.registrarConsentimiento(estudianteId, rechazo);

        // Assert
        assertTrue(politica.getConsentimientos().contains(rechazo));
        assertFalse(politica.estaAceptadaPor(estudianteId));
        verify(repositorioMock).guardar(politica);
    }

    @Test
    @DisplayName("registrarConsentimiento: lanza excepcion si no hay politica vigente")
    void registrarConsentimiento_sinPolitica_lanzaNotFoundSinGuardar() {
        // Arrange
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);
        when(repositorioMock.buscarVigente()).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PoliticaPrivacidadNotFoundException.class,
                () -> servicio.registrarConsentimiento(estudianteId, consentimiento));
        verify(repositorioMock, never()).guardar(any());
    }

    @Test
    @DisplayName("registrarConsentimiento: rechaza parametros nulos sin tocar el repositorio")
    void registrarConsentimiento_parametrosNulos_lanzaExcepcion() {
        // Arrange
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> servicio.registrarConsentimiento(null, consentimiento));
        assertThrows(NullPointerException.class, () -> servicio.registrarConsentimiento(estudianteId, null));
        verifyNoInteractions(repositorioMock);
    }

    @Test
    @DisplayName("registrarConsentimiento: rechaza el consentimiento de otro estudiante")
    void registrarConsentimiento_deOtroEstudiante_lanzaExcepcion() {
        // Arrange
        Consentimiento ajeno = new Consentimiento(UUID.randomUUID(), true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrarConsentimiento(estudianteId, ajeno));
        verifyNoInteractions(repositorioMock);
    }
}
