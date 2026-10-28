package application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class Modulo3ServiceTest {

    @Mock
    private IModulo3Repository repositoryMock; // Interfaz mockeada

    @InjectMocks
    private Modulo3ApplicationService service; // Servicio a probar

    @Test
    void testEjecutarCasoDeUsoConMock() {
        // Arrange: Configurar el comportamiento simulado del repositorio
        when(repositoryMock.obtenerPorId("M3-001")).thenReturn(new EntidadModulo3("ObjetoPrueba", 100));

        // Act: Ejecutar la lógica de la capa de aplicación
        EntidadModulo3 resultado = service.procesarSolicitud("M3-001");

        // Assert: Verificar interacción y resultado
        assertNotNull(resultado);
        verify(repositoryMock, times(1)).obtenerPorId("M3-001");
    }
}
