package infrastructure.BC3_SupportChat;

import BC3_SoporteChat.ChatSession;
import BC3_SoporteChat.ChatSessionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryChatSessionRepository implements ChatSessionRepository {

    private final ConcurrentMap<UUID, ChatSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void save(ChatSession chatSession) {
        Objects.requireNonNull(chatSession, "La sesion de chat es obligatoria");
        sessions.put(chatSession.getId(), chatSession);
    }

    @Override
    public Optional<ChatSession> findById(UUID id) {
        Objects.requireNonNull(id, "El id del chat es obligatorio");
        return Optional.ofNullable(sessions.get(id));
    }

    @Override
    public List<ChatSession> findActiveByPsychologist(UUID psychologistId) {
        Objects.requireNonNull(psychologistId, "El id del psicologo es obligatorio");

        List<ChatSession> activeSessions = new ArrayList<>();
        for (ChatSession session : sessions.values()) {
            if (isActiveForPsychologist(session, psychologistId)) {
                activeSessions.add(session);
            }
        }

        return activeSessions;
    }

    private boolean isActiveForPsychologist(ChatSession session, UUID psychologistId) {
        return session.isActive() && session.isAssignedTo(psychologistId);
    }
}
