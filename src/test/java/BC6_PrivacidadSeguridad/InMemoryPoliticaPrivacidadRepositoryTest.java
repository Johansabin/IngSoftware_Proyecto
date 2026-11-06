package BC6_PrivacidadSeguridad;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import infrastructure.BC6_PrivacidadSeguridad.InMemoryPoliticaPrivacidadRepository;

/**
 * Issue #10 - Capa de Repositorio del Modulo 6 (SetUp / TearDown).
 *
 * <pre>
 * Clase/Metodo        | Caso de prueba                    | Valores de prueba        | Resultado esperado
 * --------------------+-----------------------------------+--------------------------+------------------------------
 * guardar             | Guardar y buscar por version      | politica "v1.0"          | buscarPorVersion la devuelve
 * guardar             | Misma version                     | dos politicas "v1.0"     | la segunda reemplaza a la primera
 * guardar             | Guardar nulo                      | null                     | NullPointerException
 * buscarVigente       | Repositorio vacio                 | -                        | Optional vacio
 * buscarVigente       | Varias politicas                  | v1.0, v2.0               | la ultima guardada (v2.0)
 * buscarVigente       | Re-guardar una version anterior   | v1.0, v2.0, v1.0 de nuevo| v1.0 pasa a ser la vigente
 * buscarPorVersion    | Version inexistente               | "v9.9"                   | Optional vacio
 * buscarPorVersion    | Version nula                      | null                     | NullPointerException
 * </pre>
 */
class InMemoryPoliticaPrivacidadRepositoryTest {

    private PoliticaPrivacidadRepository repositorio;
    private PoliticaPrivacidad politicaV1;
    private PoliticaPrivacidad politicaV2;

    @BeforeEach
    void setUp() {
        // Arrange (comun): repositorio limpio y dos politicas de versiones distintas
        repositorio = new InMemoryPoliticaPrivacidadRepository();
        politicaV1 = new PoliticaPrivacidad("v1.0", "Contenido inicial");
        politicaV2 = new PoliticaPrivacidad("v2.0", "Contenido actualizado");
    }

    @AfterEach
    void tearDown() {
        // Limpieza: descarta el repositorio y los objetos de la prueba
        repositorio = null;
        politicaV1 = null;
        politicaV2 = null;
    }

    @Test
    @DisplayName("guardar: la politica guardada se recupera por version")
    void guardar_politicaValida_sePuedeBuscarPorVersion() {
        // Arrange (en setUp)

        // Act
        repositorio.guardar(politicaV1);

        // Assert
        assertSame(politicaV1, repositorio.buscarPorVersion("v1.0").orElseThrow());
    }

    @Test
    @DisplayName("guardar: otra politica con la misma version reemplaza a la anterior")
    void guardar_mismaVersion_reemplazaLaAnterior() {
        // Arrange
        PoliticaPrivacidad reemplazo = new PoliticaPrivacidad("v1.0", "Contenido corregido");
        repositorio.guardar(politicaV1);

        // Act
        repositorio.guardar(reemplazo);

        // Assert
        assertSame(reemplazo, repositorio.buscarPorVersion("v1.0").orElseThrow());
    }

    @Test
    @DisplayName("guardar: rechaza una politica nula")
    void guardar_nula_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> repositorio.guardar(null));
    }

    @Test
    @DisplayName("buscarVigente: vacio si no hay politicas")
    void buscarVigente_repositorioVacio_devuelveVacio() {
        // Arrange (en setUp)

        // Act & Assert
        assertFalse(repositorio.buscarVigente().isPresent());
    }

    @Test
    @DisplayName("buscarVigente: la vigente es la ultima politica guardada")
    void buscarVigente_variasPoliticas_devuelveLaUltima() {
        // Arrange
        repositorio.guardar(politicaV1);
        repositorio.guardar(politicaV2);

        // Act
        PoliticaPrivacidad vigente = repositorio.buscarVigente().orElseThrow();

        // Assert
        assertSame(politicaV2, vigente);
    }

    @Test
    @DisplayName("buscarVigente: re-guardar una version anterior la vuelve vigente")
    void buscarVigente_reguardarVersionAnterior_pasaAVigente() {
        // Arrange
        repositorio.guardar(politicaV1);
        repositorio.guardar(politicaV2);

        // Act
        repositorio.guardar(politicaV1);

        // Assert
        assertSame(politicaV1, repositorio.buscarVigente().orElseThrow());
    }

    @Test
    @DisplayName("buscarPorVersion: vacio si la version no existe")
    void buscarPorVersion_inexistente_devuelveVacio() {
        // Arrange
        repositorio.guardar(politicaV1);

        // Act & Assert
        assertTrue(repositorio.buscarPorVersion("v9.9").isEmpty());
    }

    @Test
    @DisplayName("buscarPorVersion: rechaza una version nula")
    void buscarPorVersion_nula_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> repositorio.buscarPorVersion(null));
    }
}
