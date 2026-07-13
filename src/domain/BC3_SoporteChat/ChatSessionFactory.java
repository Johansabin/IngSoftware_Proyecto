package BC3_SoporteChat;

import java.util.Objects;
import java.util.UUID;

public final class ChatSessionFactory {
    private ChatSessionFactory() {
    }

    public static ChatSession startAnonymousSession(UUID psychologistId, String pseudonym) {
        Objects.requireNonNull(psychologistId, "The psychologist is required");

        AnonymousPseudonym anonymousPseudonym = new AnonymousPseudonym(pseudonym);
        return new ChatSession(anonymousPseudonym, psychologistId);
    }
}

