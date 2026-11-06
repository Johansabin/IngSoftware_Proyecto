package application.BC2_SeguimientoEmocional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import BC2_SeguimientoEmocional.BitacoraEmocional;
import BC2_SeguimientoEmocional.BitacoraEmocionalRepository;
import BC2_SeguimientoEmocional.Emocion;
import BC2_SeguimientoEmocional.EscalaEmocional;
import BC2_SeguimientoEmocional.ResumenSemanal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Issue #9 - Pruebas de Servicio de Aplicacion con Mocks del Modulo 2
 * (Seguimiento Emocional). El repositorio se simula con Mockito: no se toca
 * ninguna persistencia real.
 *
 * <pre>
 * Clase/Metodo            | Caso de prueba                      | Valores de prueba            | Resultado esperado
 * ------------------------+-------------------------------------+------------------------------+---------------------------------------
 * constructor             | Repositorio nulo                    | null                         | NullPointerException
 * registrarEmocion        | Bitacora existente                  | repo devuelve bitacora       | emocion agregada y guardar(bitacora)
 * registrarEmocion        | Estudiante sin bitacora             | repo devuelve vacio          | se crea bitacora nueva y se guarda
 * registrarEmocion        | Parametros nulos                    | null en id o emocion         | NullPointerException, sin guardar
 * obtenerResumenSemanal   | Bitacora con registros              | escalas 8 y 2 en la semana   | promedio 5.0, 1 dia de paz, se guarda
 * obtenerResumenSemanal   | Estudiante sin bitacora             | repo devuelve vacio          | BitacoraNotFoundException, sin guardar
 * obtenerResumenSemanal   | Parametros nulos                    | null en id o semana          | NullPointerException, sin consultar
 * obtenerHistorial        | Bitacora con registros              | 2 emociones                  | lista con las 2 emociones
 * obtenerHistorial        | Estudiante sin bitacora             | repo devuelve vacio          | lista vacia
 * obtenerHistorial        | Id nulo                             | null                         | NullPointerException
 * </pre>
 */
@ExtendWith(MockitoExtension.class)
class BitacoraEmocionalAppServiceImplTest {

    // 2026-11-02 es lunes
    private static final LocalDate LUNES = LocalDate.of(2026, 11, 2);

    @Mock
    private BitacoraEmocionalRepository repositorioMock;

    private BitacoraEmocionalAppServiceImpl servicio;
    private UUID estudianteId;

    @BeforeEach
    void setUp() {
        // Arrange (comun)
        servicio = new BitacoraEmocionalAppServiceImpl(repositorioMock);
        estudianteId = UUID.randomUUID();
    }

    private static Emocion emocion(LocalDate fecha, String tipo, int valor) {
        return new Emocion(fecha, tipo, new EscalaEmocional(valor), null);
    }

    @Test
    @DisplayName("constructor: rechaza un repositorio nulo")
    void constructor_repositorioNulo_lanzaExcepcion() {
        // Arrange & Act & Assert
        assertThrows(NullPointerException.class, () -> new BitacoraEmocionalAppServiceImpl(null));
    }

    // -------------------------------------------------------- registrarEmocion

    @Test
    @DisplayName("registrarEmocion: agrega la emocion a la bitacora existente y la guarda")
    void registrarEmocion_bitacoraExistente_agregaYGuarda() {
        // Arrange
        BitacoraEmocional bitacora = new BitacoraEmocional(estudianteId);
        Emocion estres = emocion(LUNES, "estres", 8);
        when(repositorioMock.buscarPorEstudiante(estudianteId)).thenReturn(Optional.of(bitacora));

        // Act
        servicio.registrarEmocion(estudianteId, estres);

        // Assert
        assertEquals(1, bitacora.getRegistros().size());
        assertSame(estres, bitacora.getRegistros().get(0));
        verify(repositorioMock, times(1)).guardar(bitacora);
    }

    @Test
    @DisplayName("registrarEmocion: crea una bitacora nueva si el estudiante no tiene")
    void registrarEmocion_sinBitacora_creaYGuarda() {
        // Arrange
        Emocion calma = emocion(LUNES, "calma", 2);
        when(repositorioMock.buscarPorEstudiante(estudianteId)).thenReturn(Optional.empty());

        // Act
        servicio.registrarEmocion(estudianteId, calma);

        // Assert
        ArgumentCaptor<BitacoraEmocional> captor = ArgumentCaptor.forClass(BitacoraEmocional.class);
        verify(repositorioMock).guardar(captor.capture());
        BitacoraEmocional guardada = captor.getValue();
        assertEquals(estudianteId, guardada.getEstudianteId());
        assertEquals(1, guardada.getRegistros().size());
    }

    @Test
    @DisplayName("registrarEmocion: rechaza parametros nulos sin tocar el repositorio")
    void registrarEmocion_parametrosNulos_lanzaExcepcion() {
        // Arrange
        Emocion calma = emocion(LUNES, "calma", 2);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> servicio.registrarEmocion(null, calma));
        assertThrows(NullPointerException.class, () -> servicio.registrarEmocion(estudianteId, null));
        verifyNoInteractions(repositorioMock);
    }

    // ----------------------------------------------------- obtenerResumenSemanal

    @Test
    @DisplayName("obtenerResumenSemanal: calcula el resumen de la semana y guarda la bitacora")
    void obtenerResumenSemanal_bitacoraConRegistros_devuelveResumen() {
        // Arrange
        BitacoraEmocional bitacora = new BitacoraEmocional(estudianteId);
        bitacora.agregarRegistro(emocion(LUNES, "estres", 8));
        bitacora.agregarRegistro(emocion(LUNES.plusDays(1), "calma", 2));
        when(repositorioMock.buscarPorEstudiante(estudianteId)).thenReturn(Optional.of(bitacora));

        // Act
        ResumenSemanal resumen = servicio.obtenerResumenSemanal(estudianteId, LUNES);

        // Assert
        assertNotNull(resumen);
        assertEquals(LUNES, resumen.getSemanaInicio());
        assertEquals(5.0, resumen.getPromedioEstres(), 0.0001);
        assertEquals(1, resumen.getDiasDePaz());
        verify(repositorioMock).guardar(bitacora);
    }

    @Test
    @DisplayName("obtenerResumenSemanal: lanza excepcion si el estudiante no tiene bitacora")
    void obtenerResumenSemanal_sinBitacora_lanzaNotFound() {
        // Arrange
        when(repositorioMock.buscarPorEstudiante(estudianteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BitacoraNotFoundException.class,
                () -> servicio.obtenerResumenSemanal(estudianteId, LUNES));
        verify(repositorioMock, never()).guardar(any());
    }

    @Test
    @DisplayName("obtenerResumenSemanal: rechaza parametros nulos sin tocar el repositorio")
    void obtenerResumenSemanal_parametrosNulos_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> servicio.obtenerResumenSemanal(null, LUNES));
        assertThrows(NullPointerException.class, () -> servicio.obtenerResumenSemanal(estudianteId, null));
        verifyNoInteractions(repositorioMock);
    }

    // ------------------------------------------------------- obtenerHistorial

    @Test
    @DisplayName("obtenerHistorial: devuelve los registros de la bitacora del estudiante")
    void obtenerHistorial_bitacoraConRegistros_devuelveRegistros() {
        // Arrange
        BitacoraEmocional bitacora = new BitacoraEmocional(estudianteId);
        bitacora.agregarRegistro(emocion(LUNES, "estres", 8));
        bitacora.agregarRegistro(emocion(LUNES.plusDays(1), "calma", 2));
        when(repositorioMock.buscarPorEstudiante(estudianteId)).thenReturn(Optional.of(bitacora));

        // Act
        List<Emocion> historial = servicio.obtenerHistorial(estudianteId);

        // Assert
        assertEquals(2, historial.size());
        verify(repositorioMock, never()).guardar(any());
    }

    @Test
    @DisplayName("obtenerHistorial: lista vacia si el estudiante no tiene bitacora")
    void obtenerHistorial_sinBitacora_devuelveListaVacia() {
        // Arrange
        when(repositorioMock.buscarPorEstudiante(estudianteId)).thenReturn(Optional.empty());

        // Act
        List<Emocion> historial = servicio.obtenerHistorial(estudianteId);

        // Assert
        assertTrue(historial.isEmpty());
    }

    @Test
    @DisplayName("obtenerHistorial: rechaza un id nulo")
    void obtenerHistorial_idNulo_lanzaExcepcion() {
        // Arrange (en setUp)

        // Act & Assert
        assertThrows(NullPointerException.class, () -> servicio.obtenerHistorial(null));
        verifyNoInteractions(repositorioMock);
    }
}
