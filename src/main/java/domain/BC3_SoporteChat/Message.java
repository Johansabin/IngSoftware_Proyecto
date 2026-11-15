package BC3_SoporteChat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message {

    private static final int MAX_CONTENT_LENGTH = 1000;

    private final UUID id;
    private final String content;
    private final SenderRole senderRole;
    private final LocalDateTime timestamp;

    public Message(String content, SenderRole senderRole) {
        this(UUID.randomUUID(), content, senderRole, LocalDateTime.now());
    }

    public Message(UUID id, String content, SenderRole senderRole, LocalDateTime timestamp) {
        this.id = Objects.requireNonNull(id, "El id del mensaje es obligatorio");
        this.content = validateContent(content);
        this.senderRole = Objects.requireNonNull(senderRole, "El rol del remitente es obligatorio");
        this.timestamp = Objects.requireNonNull(timestamp, "La fecha del mensaje es obligatoria");
    }

    private String validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("El contenido del mensaje no puede estar vacio");
        }

        String normalizedContent = content.trim();
        if (normalizedContent.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("El contenido del mensaje supera la longitud permitida");
        }

        return normalizedContent;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public SenderRole getSenderRole() {
        return senderRole;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
