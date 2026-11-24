package BC3_SoporteChat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class ChatSessionTest {

    @Test
    void anonymousPseudonym_generaAliasConPrefijoYValidaVacio() {
        // Arrange & Act
        AnonymousPseudonym generado = AnonymousPseudonym.generate();
        AnonymousPseudonym personalizado = new AnonymousPseudonym("  alias-123  ");

        // Assert
        assertTrue(generado.getAlias().startsWith("estudiante-"));
        assertEquals("alias-123", personalizado.getAlias());
        assertThrows(IllegalArgumentException.class, () -> new AnonymousPseudonym(null));
        assertThrows(IllegalArgumentException.class, () -> new AnonymousPseudonym("   "));
    }

    @Test
    void message_normalizaContenidoYValidaInvariantes() {
        // Arrange & Act
        Message mensaje = new Message("  Hola psicologo  ", SenderRole.STUDENT);
        String demasiadoLargo = "a".repeat(1001);

        // Assert
        assertNotNull(mensaje.getId());
        assertEquals("Hola psicologo", mensaje.getContent());
        assertEquals(SenderRole.STUDENT, mensaje.getSenderRole());
        assertNotNull(mensaje.getTimestamp());
        assertThrows(IllegalArgumentException.class, () -> new Message(null, SenderRole.STUDENT));
        assertThrows(IllegalArgumentException.class, () -> new Message("   ", SenderRole.PSYCHOLOGIST));
        assertThrows(IllegalArgumentException.class, () -> new Message(demasiadoLargo, SenderRole.STUDENT));
        assertThrows(NullPointerException.class, () -> new Message("ok", null));
    }

    @Test
    void auditData_registraCreacionYActualizacion() {
        // Arrange
        AuditData auditData = new AuditData();

        // Act
        auditData.registerUpdate();

        // Assert
        assertNotNull(auditData.getCreatedAt());
        assertNotNull(auditData.getUpdatedAt());
        assertFalse(auditData.getUpdatedAt().isBefore(auditData.getCreatedAt()));
    }

    @Test
    void chatSession_flujoCompletoDeEnvioYCierre() {
        // Arrange
        UUID psicologoId = UUID.randomUUID();
        UUID otroPsicologoId = UUID.randomUUID();
        ChatSession sesion = ChatSessionFactory.start(psicologoId);
        Message mensaje = new Message("Necesito apoyo", SenderRole.STUDENT);

        // Act
        sesion.sendMessage(mensaje);

        // Assert
        assertNotNull(sesion.getId());
        assertNotNull(sesion.getStudent());
        assertEquals(psicologoId, sesion.getPsychologistId());
        assertEquals(ChatSessionStatus.ACTIVE, sesion.getStatus());
        assertTrue(sesion.isActive());
        assertTrue(sesion.isAssignedTo(psicologoId));
        assertFalse(sesion.isAssignedTo(otroPsicologoId));
        assertEquals(1, sesion.getMessages().size());
        assertNotNull(sesion.getAuditData());

        // Act: cerrar dos veces (idempotente)
        sesion.close();
        sesion.close();

        // Assert: cerrada no acepta mas mensajes
        assertEquals(ChatSessionStatus.CLOSED, sesion.getStatus());
        assertFalse(sesion.isActive());
        assertThrows(IllegalStateException.class, () -> sesion.sendMessage(mensaje));
    }
}
