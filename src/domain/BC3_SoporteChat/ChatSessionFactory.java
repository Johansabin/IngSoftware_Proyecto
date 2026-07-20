package BC3_SoporteChat;

import java.util.Objects;
import java.util.UUID;

public final class ChatSessionFactory {
    private ChatSessionFactory() {
    }

    public static ChatSession start(UUID psychologistId) {
        Objects.requireNonNull(psychologistId, "The psychologist is required");
        return new ChatSession(AnonymousPseudonym.generate(), psychologistId);
    }
}
