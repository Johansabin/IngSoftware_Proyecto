package BC3_SoporteChat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatSessionRepository {
    void save(ChatSession chatSession);

    Optional<ChatSession> findById(UUID id);

    List<ChatSession> findActiveByPsychologist(UUID psychologistId);
}

