package BC3_SoporteChat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message {
    private static final int MAX_CONTENT_LENGTH = 500;

    private UUID id;
    private String content;
    private SenderRole senderRole;
    private LocalDateTime sentAt;

    public Message() {
    }

    public Message(String content, SenderRole senderRole) {
        this.id = UUID.randomUUID();
        this.content = validateContent(content);
        this.senderRole = Objects.requireNonNull(senderRole, "The sender role is required");
        this.sentAt = LocalDateTime.now();
    }

    private String validateContent(String content) {
        Objects.requireNonNull(content, "The message content is required");

        String normalizedContent = content.trim();
        if (normalizedContent.isBlank()) {
            throw new IllegalArgumentException("The message content cannot be blank");
        }

        if (normalizedContent.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("The message content exceeds the allowed length");
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

    public LocalDateTime getSentAt() {
        return sentAt;
    }
}

