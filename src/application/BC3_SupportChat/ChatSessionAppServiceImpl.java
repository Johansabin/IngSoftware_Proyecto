package application.BC3_SupportChat;

import BC3_SoporteChat.ChatSession;
import BC3_SoporteChat.ChatSessionFactory;
import BC3_SoporteChat.ChatSessionRepository;
import BC3_SoporteChat.Message;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatSessionAppServiceImpl implements ChatSessionAppService {

    private final ChatSessionRepository chatSessionRepository;

    public ChatSessionAppServiceImpl(ChatSessionRepository chatSessionRepository) {
        this.chatSessionRepository = Objects.requireNonNull(
                chatSessionRepository,
                "El repositorio de chat es obligatorio");
    }

    @Override
    public UUID startChat(StartChatRequest request) {
        Objects.requireNonNull(request, "La solicitud de inicio de chat es obligatoria");

        ChatSession chatSession = createChatSession(request);
        saveChatSession(chatSession);
        return chatSession.getId();
    }

    @Override
    public void sendMessage(SendMessageRequest request) {
        Objects.requireNonNull(request, "La solicitud de envio de mensaje es obligatoria");

        ChatSession chatSession = getChat(request.getChatId());
        chatSession.sendMessage(createMessage(request));
        saveChatSession(chatSession);
    }

    @Override
    public void closeChat(UUID chatId) {
        ChatSession chatSession = getChat(chatId);
        chatSession.close();
        saveChatSession(chatSession);
    }

    @Override
    public ChatSession getChat(UUID chatId) {
        Objects.requireNonNull(chatId, "El id del chat es obligatorio");

        return chatSessionRepository.findById(chatId)
                .orElseThrow(() -> new ChatSessionNotFoundException(chatId));
    }

    @Override
    public List<ChatSession> getActiveChatsByPsychologist(UUID psychologistId) {
        Objects.requireNonNull(psychologistId, "El id del psicologo es obligatorio");
        return chatSessionRepository.findActiveByPsychologist(psychologistId);
    }

    private ChatSession createChatSession(StartChatRequest request) {
        return ChatSessionFactory.start(request.getPsychologistId());
    }

    private Message createMessage(SendMessageRequest request) {
        return new Message(request.getContent(), request.getSenderRole());
    }

    private void saveChatSession(ChatSession chatSession) {
        chatSessionRepository.save(chatSession);
    }
}
