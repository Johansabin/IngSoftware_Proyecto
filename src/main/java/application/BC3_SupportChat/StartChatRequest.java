package application.BC3_SupportChat;

import java.util.Objects;
import java.util.UUID;

public class StartChatRequest {

    private final UUID psychologistId;

    public StartChatRequest(UUID psychologistId) {
        this.psychologistId = Objects.requireNonNull(psychologistId, "El id del psicologo es obligatorio");
    }

    public UUID getPsychologistId() {
        return psychologistId;
    }
}
