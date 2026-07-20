package BC3_SoporteChat;

import java.util.Objects;
import java.util.UUID;

public class ChatSessionFactory {

    private ChatSessionFactory() {
    }

    public static ChatSession start(UUID psychologistId) {
        Objects.requireNonNull(psychologistId, "El id del psicologo es obligatorio");
        return new ChatSession(AnonymousPseudonym.generate(), psychologistId);
    }
}
