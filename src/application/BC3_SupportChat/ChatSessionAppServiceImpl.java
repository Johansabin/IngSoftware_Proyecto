package application.BC3_SupportChat;

import BC3_SoporteChat.ChatSession;
import BC3_SoporteChat.ChatSessionFactory;
import BC3_SoporteChat.ChatSessionRepository;
import BC3_SoporteChat.Message;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatSessionAppServiceImpl implements ChatSessionAppService {

    private final ChatSessionRepository repository;

    public ChatSessionAppServiceImpl(ChatSessionRepository repository) {
        this.repository = Objects.requireNonNull(repository, "El repositorio de chat es obligatorio");
    }

    @Override
    public UUID startChat(StartChatRequest request) {
        Objects.requireNonNull(request, "La solicitud de inicio de chat es obligatoria");

        ChatSession chat = ChatSessionFactory.start(request.getPsychologistId());
        repository.save(chat);
        return chat.getId();
    }

    @Override
    public void sendMessage(SendMessageRequest request) {
        Objects.requireNonNull(request, "La solicitud de envio de mensaje es obligatoria");

        // Flujo de aplicacion: buscar sesion, crear mensaje, actualizar agregado y guardar.
        ChatSession chat = getChat(request.getChatId());
        Message message = new Message(request.getContent(), request.getSenderRole());
        chat.sendMessage(message);
        repository.save(chat);
    }

    @Override
    public void closeChat(UUID chatId) {
        ChatSession chat = getChat(chatId);
        chat.close();
        repository.save(chat);
    }

    @Override
    public ChatSession getChat(UUID chatId) {
        Objects.requireNonNull(chatId, "El id del chat es obligatorio");

        return repository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("No existe una sesion de chat con el id indicado"));
    }

    @Override
    public List<ChatSession> getActiveChatsByPsychologist(UUID psychologistId) {
        Objects.requireNonNull(psychologistId, "El id del psicologo es obligatorio");
        return repository.findActiveByPsychologist(psychologistId);
    }
}
