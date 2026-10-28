package BC5_Notificaciones;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class RecordatorioTest {

    @Test
    public void Constructor_MensajeVacio_LanzaExcepcion() {
        // Arrange
        UUID estudianteId = UUID.randomUUID();
        String mensajeInvalido = "";
        LocalDateTime fechaValida = LocalDateTime.now().plusDays(1);

        // Act & Assert
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class,
            () -> new Recordatorio(estudianteId, mensajeInvalido, fechaValida)
        );
        
        assertNotNull(excepcion);
    }
    
    @Test
    public void Constructor_DatosValidos_CreaInstancia() {
        // Arrange
        UUID estudianteId = UUID.randomUUID();
        String mensajeValido = "Cita con el psicólogo";
        LocalDateTime fechaValida = LocalDateTime.now().plusDays(1);

        // Act
        Recordatorio recordatorio = new Recordatorio(estudianteId, mensajeValido, fechaValida);

        // Assert
        assertNotNull(recordatorio);
        assertEquals(mensajeValido, recordatorio.getMensaje());
        assertEquals(estudianteId, recordatorio.getEstudianteId());
        assertEquals(fechaValida, recordatorio.getFechaProgramada());
        assertTrue(recordatorio.isActivo());
    }

    @Test
    public void Desactivar_RecordatorioActivo_QuedaInactivo() {
        // Arrange
        UUID estudianteId = UUID.randomUUID();
        Recordatorio recordatorio = new Recordatorio(estudianteId, "Revisar tarea", LocalDateTime.now().plusDays(1));

        // Act
        recordatorio.desactivar();

        // Assert
        assertFalse(recordatorio.isActivo());
    }
}
