package presentation.BC3_SupportChat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import BC3_SoporteChat.ChatSessionRepository;
import BC3_SoporteChat.ChatSessionStatus;
import BC3_SoporteChat.SenderRole;
import application.BC3_SupportChat.ChatSessionAppService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SupportChatPresentationTest {

    private static final String BOUNDED_CONTEXT = "boundedContext";

    @Test
    void supportChatController_flujoCompletoHttpYDtos() {
        // Arrange
        SupportChatConfiguration config = new SupportChatConfiguration();
        ChatSessionRepository repository = config.chatSessionRepository();
        ChatSessionAppService appService = config.chatSessionAppService(repository);
        SupportChatController controller = new SupportChatController(appService);

        UUID psicologoId = UUID.randomUUID();
        StartChatHttpRequest startReq = new StartChatHttpRequest();
        startReq.setPsychologistId(psicologoId);

        // Act: iniciar chat
        ChatSessionResponse creada = controller.startChat(startReq);

        SendMessageHttpRequest msgReq = new SendMessageHttpRequest();
        msgReq.setContent("Mensaje de prueba");
        msgReq.setSenderRole(SenderRole.STUDENT);
        ChatSessionResponse conMensaje = controller.sendMessage(creada.getId(), msgReq);

        List<ChatSessionResponse> activas = controller.getActiveChatsByPsychologist(psicologoId);
        ChatSessionResponse cerrada = controller.closeChat(creada.getId());

        // Assert
        assertNotNull(creada.getId());
        assertTrue(creada.getStudentPseudonym().startsWith("estudiante-"));
        assertEquals(psicologoId, creada.getPsychologistId());
        assertEquals(ChatSessionStatus.ACTIVE, creada.getStatus());

        assertEquals(1, conMensaje.getMessages().size());
        MessageResponse msgResp = conMensaje.getMessages().get(0);
        assertNotNull(msgResp.getId());
        assertEquals("Mensaje de prueba", msgResp.getContent());
        assertEquals(SenderRole.STUDENT, msgResp.getSenderRole());
        assertNotNull(msgResp.getSentAt());

        assertEquals(1, activas.size());
        assertEquals(ChatSessionStatus.CLOSED, cerrada.getStatus());
    }

    @Test
    void supportChatDemoRunner_ejecutaDemoSinErrores() {
        // Arrange
        SupportChatConfiguration config = new SupportChatConfiguration();
        ChatSessionAppService appService = config.chatSessionAppService(config.chatSessionRepository());
        SupportChatDemoRunner runner = new SupportChatDemoRunner(appService);

        // Act
        runner.run();

        // Assert
        UUID demoPsychologist = UUID.fromString("11111111-1111-1111-1111-111111111111");
        assertEquals(1, appService.getActiveChatsByPsychologist(demoPsychologist).size());
    }

    @Test
    void menteEnCasaModulesController_devuelveDemosDeTodosLosModulos() {
        // Arrange
        MenteEnCasaModulesController controller = new MenteEnCasaModulesController();

        // Act
        Map<String, Object> bc1 = controller.authenticationDemo();
        Map<String, Object> bc2 = controller.emotionalTrackingDemo();
        Map<String, Object> bc4 = controller.recommendationsDemo();
        Map<String, Object> bc5 = controller.notificationsDemo();
        Map<String, Object> bc6 = controller.privacySecurityDemo();

        // Assert
        assertEquals("BC1 - Autenticacion", bc1.get(BOUNDED_CONTEXT));
        assertEquals(Boolean.TRUE, bc1.get("codigoValido"));

        assertEquals("BC2 - Seguimiento Emocional", bc2.get(BOUNDED_CONTEXT));
        assertEquals(2, bc2.get("registros"));

        assertEquals("BC4 - Recomendaciones", bc4.get(BOUNDED_CONTEXT));
        assertEquals(2, bc4.get("total"));

        assertEquals("BC5 - Notificaciones", bc5.get(BOUNDED_CONTEXT));
        assertEquals(Boolean.TRUE, bc5.get("activo"));

        assertEquals("BC6 - Privacidad y Seguridad", bc6.get(BOUNDED_CONTEXT));
        assertEquals(Boolean.TRUE, bc6.get("estudianteAcepto"));
        assertFalse(bc6.isEmpty());
    }
}
