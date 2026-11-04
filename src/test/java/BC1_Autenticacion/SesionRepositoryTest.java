package BC1_Autenticacion;

import infrastructure.BC1_Autenticacion.InMemorySesionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas Unitarias de Repositorio - InMemorySesionRepository (SetUp / TearDown)")
class SesionRepositoryTest {

    private InMemorySesionRepository repository;

    @BeforeEach
    void setUp() {
        // Inicializar entorno limpio antes de cada prueba (SetUp)
        repository = new InMemorySesionRepository();
    }

    @AfterEach
    void tearDown() {
        // Limpiar entorno después de cada prueba (TearDown)
        repository.limpiar();
    }

    @Test
    @DisplayName("Debe guardar y recuperar una sesión por su identificador UUID")
    void guardarYBuscarPorIdExitoso() {
        // Arrange
        Sesion sesion = new Sesion("20214567");
        UUID id = sesion.getId();

        // Act
        repository.guardar(sesion);
        Optional<Sesion> encontrada = repository.buscarPorId(id);

        // Assert
        assertTrue(encontrada.isPresent());
        assertEquals(id, encontrada.get().getId());
        assertEquals("20214567", encontrada.get().getCodigoUsuario());
        assertEquals(1, repository.contar());
    }

    @Test
    @DisplayName("Debe retornar Optional vacío al buscar por ID inexistente")
    void buscarPorIdInexistenteRetornaVacio() {
        // Arrange
        UUID idNoRegistrado = UUID.randomUUID();

        // Act
        Optional<Sesion> resultado = repository.buscarPorId(idNoRegistrado);

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Debe recuperar la sesión activa asociada al código de usuario")
    void buscarActivaPorCodigoExitoso() {
        // Arrange
        String codigoUsuario = "20218888";
        Sesion sesion = new Sesion(codigoUsuario);
        repository.guardar(sesion);

        // Act
        Optional<Sesion> activa = repository.buscarActivaPorCodigo(codigoUsuario);

        // Assert
        assertTrue(activa.isPresent());
        assertEquals(sesion.getId(), activa.get().getId());
        assertTrue(activa.get().isActiva());
    }

    @Test
    @DisplayName("Debe no retornar una sesión en buscarActiva si fue desactivada")
    void actualizarSesionInactivaNoRetornaEnBuscarActiva() {
        // Arrange
        String codigoUsuario = "20211234";
        Sesion sesion = new Sesion(codigoUsuario);
        repository.guardar(sesion);

        // Act
        sesion.desactivar();
        repository.guardar(sesion);
        Optional<Sesion> activa = repository.buscarActivaPorCodigo(codigoUsuario);
        Optional<Sesion> porId = repository.buscarPorId(sesion.getId());

        // Assert
        assertTrue(activa.isEmpty());
        assertTrue(porId.isPresent());
        assertFalse(porId.get().isActiva());
    }

    @Test
    @DisplayName("Debe retornar Optional vacío si el código buscado no tiene sesión activa")
    void buscarActivaIgnoraOtrosUsuarios() {
        // Arrange
        Sesion sesionUsuarioA = new Sesion("20210001");
        repository.guardar(sesionUsuarioA);

        // Act
        Optional<Sesion> resultadoUsuarioB = repository.buscarActivaPorCodigo("20210002");

        // Assert
        assertTrue(resultadoUsuarioB.isEmpty());
    }

    @Test
    @DisplayName("Debe lanzar NullPointerException al intentar guardar una sesión nula")
    void guardarSesionNulaLanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> {
            repository.guardar(null);
        });
    }

    @Test
    @DisplayName("Debe lanzar NullPointerException al buscar por ID nulo")
    void buscarPorIdNuloLanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> {
            repository.buscarPorId(null);
        });
    }

    @Test
    @DisplayName("Debe retornar Optional vacío al buscar activa con código nulo o en blanco")
    void buscarActivaConCodigoNuloOBlancoRetornaVacio() {
        // Arrange & Act & Assert
        assertTrue(repository.buscarActivaPorCodigo(null).isEmpty());
        assertTrue(repository.buscarActivaPorCodigo("").isEmpty());
        assertTrue(repository.buscarActivaPorCodigo("   ").isEmpty());
    }
}
