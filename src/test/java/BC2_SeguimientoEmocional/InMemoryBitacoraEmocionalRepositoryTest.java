package BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import infrastructure.BC2_SeguimientoEmocional.InMemoryBitacoraEmocionalRepository;

/**
 * Issue #8 - Pruebas de Repositorio (SetUp / TearDown) del Modulo 2
 * (Seguimiento Emocional).
 *
 * <p>Casos de prueba especificados:</p>
 * <pre>
 * Clase/Metodo                     | Caso de prueba                     | Valores de prueba               | Resultado esperado
 * ---------------------------------+------------------------------------+---------------------------------+------------------------------
 * guardar                          | Guardar una bitacora valida        | bitacora de un estudiante       | buscarPorEstudiante la devuelve
 * guardar                          | Guardar dos veces el mismo alumno  | dos bitacoras, mismo estudiante | la segunda reemplaza a la primera
 * guardar                          | Guardar nulo                       | null                            | NullPointerException
 * buscarPorEstudiante              | Estudiante sin bitacora            | UUID aleatorio                  | Optional vacio
 * buscarPorEstudiante              | Estudiante nulo                    | null                            | NullPointerException
 * buscarPorEstudiante              | Aislamiento entre estudiantes      | dos estudiantes                 | cada uno recibe su bitacora
 * buscarPorRangoFecha              | Registros dentro del rango         | 3 registros, rango parcial      | solo los incluidos
 * buscarPorRangoFecha              | Limites inclusivos                 | fechas = desde y = hasta        | ambos incluidos
 * buscarPorRangoFecha              | Estudiante sin bitacora            | UUID aleatorio                  | lista vacia
 * buscarPorRangoFecha              | Rango sin coincidencias            | rango sin registros             | lista vacia
 * buscarPorRangoFecha              | Parametros nulos                   | null en id, desde o hasta       | NullPointerException
 * </pre>
 */
class InMemoryBitacoraEmocionalRepositoryTest {

    private static final LocalDate LUNES = LocalDate.of(2026, 11, 2);

    private BitacoraEmocionalRepository repositorio;
    private UUID estudianteId;
    private BitacoraEmocional bitacora;

    @BeforeEach
    void setUp() {
        // Arrange (comun): repositorio limpio y una bitacora valida por prueba
        repositorio = new InMemoryBitacoraEmocionalRepository();
        estudianteId = UUID.randomUUID();
        bitacora = new BitacoraEmocional(estudianteId);
    }

    @AfterEach
    void tearDown() {
        // Limpieza: descarta el repositorio y los objetos de la prueba
        repositorio = null;
        bitacora = null;
        estudianteId = null;
    }

    private static Emocion emocion(LocalDate fecha, String tipo, int valor) {
        return new Emocion(fecha, tipo, new EscalaEmocional(valor), null);
    }

    // ---------------------------------------------------------------- guardar

    @Test
    @DisplayName("guardar: una bitacora valida se recupera por estudiante")
    void guardar_bitacoraValida_sePuedeRecuperar() {
        // Arrange (en setUp)

        // Act
        repositorio.guardar(bitacora);
        Optional<BitacoraEmocional> encontrada = repositorio.buscarPorEstudiante(estudianteId);

        // Assert
        assertTrue(encontrada.isPresent());
        assertSame(bitacora, encontrada.get());
    }

    @Test
    @DisplayName("guardar: guardar otra bitacora del mismo estudiante reemplaza la anterior")
    void guardar_mismoEstudiante_reemplazaLaAnterior() {
        // Arrange
        BitacoraEmocional reemplazo = new BitacoraEmocional(estudianteId);
        repositorio.guardar(bitacora);

        // Act
        repositorio.guardar(reemplazo);

        // Assert
        assertSame(reemplazo, repositorio.buscarPorEstudiante(estudianteId).orElseThrow());
    }

    @Test
    @DisplayName("guardar: rechaza una bitacora nula")
    void guardar_bitacoraNula_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> repositorio.guardar(null));
    }

    // ---------------------------------------------------- buscarPorEstudiante

    @Test
    @DisplayName("buscarPorEstudiante: devuelve vacio si el estudiante no tiene bitacora")
    void buscarPorEstudiante_sinBitacora_devuelveVacio() {
        // Arrange
        UUID desconocido = UUID.randomUUID();

        // Act
        Optional<BitacoraEmocional> resultado = repositorio.buscarPorEstudiante(desconocido);

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("buscarPorEstudiante: rechaza un id nulo")
    void buscarPorEstudiante_idNulo_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> repositorio.buscarPorEstudiante(null));
    }

    @Test
    @DisplayName("buscarPorEstudiante: cada estudiante recibe solo su bitacora")
    void buscarPorEstudiante_variosEstudiantes_noSeMezclan() {
        // Arrange
        UUID otroId = UUID.randomUUID();
        BitacoraEmocional otraBitacora = new BitacoraEmocional(otroId);
        repositorio.guardar(bitacora);
        repositorio.guardar(otraBitacora);

        // Act
        BitacoraEmocional propia = repositorio.buscarPorEstudiante(estudianteId).orElseThrow();
        BitacoraEmocional ajena = repositorio.buscarPorEstudiante(otroId).orElseThrow();

        // Assert
        assertEquals(estudianteId, propia.getEstudianteId());
        assertEquals(otroId, ajena.getEstudianteId());
    }

    // ------------------------------------------------------ buscarPorRangoFecha

    @Test
    @DisplayName("buscarPorRangoFecha: devuelve solo los registros dentro del rango")
    void buscarPorRangoFecha_registrosMixtos_devuelveSoloLosDelRango() {
        // Arrange
        bitacora.agregarRegistro(emocion(LUNES.minusDays(5), "estres", 8));
        bitacora.agregarRegistro(emocion(LUNES, "calma", 2));
        bitacora.agregarRegistro(emocion(LUNES.plusDays(2), "alegria", 3));
        repositorio.guardar(bitacora);

        // Act
        List<Emocion> resultado = repositorio.buscarPorRangoFecha(estudianteId, LUNES, LUNES.plusDays(6));

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().noneMatch(e -> e.getFecha().isBefore(LUNES)));
    }

    @Test
    @DisplayName("buscarPorRangoFecha: los limites desde y hasta son inclusivos")
    void buscarPorRangoFecha_registrosEnLosLimites_seIncluyen() {
        // Arrange
        LocalDate hasta = LUNES.plusDays(4);
        bitacora.agregarRegistro(emocion(LUNES, "calma", 2));
        bitacora.agregarRegistro(emocion(hasta, "estres", 9));
        repositorio.guardar(bitacora);

        // Act
        List<Emocion> resultado = repositorio.buscarPorRangoFecha(estudianteId, LUNES, hasta);

        // Assert
        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("buscarPorRangoFecha: lista vacia si el estudiante no tiene bitacora")
    void buscarPorRangoFecha_sinBitacora_devuelveListaVacia() {
        // Arrange
        UUID desconocido = UUID.randomUUID();

        // Act
        List<Emocion> resultado = repositorio.buscarPorRangoFecha(desconocido, LUNES, LUNES.plusDays(6));

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("buscarPorRangoFecha: lista vacia si ningun registro cae en el rango")
    void buscarPorRangoFecha_rangoSinCoincidencias_devuelveListaVacia() {
        // Arrange
        bitacora.agregarRegistro(emocion(LUNES, "estres", 8));
        repositorio.guardar(bitacora);

        // Act
        List<Emocion> resultado = repositorio.buscarPorRangoFecha(
                estudianteId, LUNES.plusDays(10), LUNES.plusDays(20));

        // Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("buscarPorRangoFecha: rechaza parametros nulos")
    void buscarPorRangoFecha_parametrosNulos_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> repositorio.buscarPorRangoFecha(null, LUNES, LUNES));
        assertThrows(NullPointerException.class,
                () -> repositorio.buscarPorRangoFecha(estudianteId, null, LUNES));
        assertThrows(NullPointerException.class,
                () -> repositorio.buscarPorRangoFecha(estudianteId, LUNES, null));
    }
}