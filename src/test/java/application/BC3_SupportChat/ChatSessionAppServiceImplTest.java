package application.BC3_SupportChat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import BC3_SoporteChat.ChatSession;
import BC3_SoporteChat.SenderRole;
import infrastructure.BC3_SupportChat.InMemoryChatSessionRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChatSessionAppServiceImplTest {

    private InMemoryChatSessionRepository repository;
    private ChatSessionAppServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryChatSessionRepository();
        service = new ChatSessionAppServiceImpl(repository);
    }

    @Test
    void flujoCompletoDeChatYListadoActivosPorPsicologo() {
        // Arrange
        UUID psicologoId = UUID.randomUUID();
        UUID otroPsicologoId = UUID.randomUUID();
        StartChatRequest inicioReq = new StartChatRequest(psicologoId);

        // Act
        UUID chatId1 = service.startChat(inicioReq);
        UUID chatId2 = service.startChat(new StartChatRequest(psicologoId));
        service.startChat(new StartChatRequest(otroPsicologoId));

        SendMessageRequest msgReq = new SendMessageRequest(chatId1, "Hola, necesito ayuda", SenderRole.STUDENT);
        service.sendMessage(msgReq);
        service.closeChat(chatId2);

        ChatSession sesion1 = service.getChat(chatId1);
        List<ChatSession> activos = service.getActiveChatsByPsychologist(psicologoId);

        // Assert
        assertNotNull(sesion1);
        assertEquals(1, sesion1.getMessages().size());
        assertEquals("Hola, necesito ayuda", sesion1.getMessages().get(0).getContent());
        assertEquals(1, activos.size());
        assertEquals(chatId1, activos.get(0).getId());
        assertFalse(service.getChat(chatId2).isActive());
    }

    @Test
    void getChatInexistente_lanzaChatSessionNotFoundException() {
        // Arrange
        UUID inexistente = UUID.randomUUID();

        // Act & Assert
        ChatSessionNotFoundException ex = assertThrows(
                ChatSessionNotFoundException.class,
                () -> service.getChat(inexistente));
        assertTrue(ex.getMessage().contains(inexistente.toString()));
    }

    @Test
    void solicitudesNulas_lanzanNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new ChatSessionAppServiceImpl(null));
        assertThrows(NullPointerException.class, () -> service.startChat(null));
        assertThrows(NullPointerException.class, () -> service.sendMessage(null));
        assertThrows(NullPointerException.class, () -> service.getChat(null));
        assertThrows(NullPointerException.class, () -> service.getActiveChatsByPsychologist(null));
        assertThrows(NullPointerException.class, () -> new StartChatRequest(null));
        assertThrows(NullPointerException.class, () -> new SendMessageRequest(null, "hola", SenderRole.STUDENT));
        assertThrows(NullPointerException.class, () -> repository.save(null));
        assertThrows(NullPointerException.class, () -> repository.findById(null));
        assertThrows(NullPointerException.class, () -> repository.findActiveByPsychologist(null));
    }
}
