package BC3_SoporteChat;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message {
    private static final int MAX_CONTENT_LENGTH = 1000;

    private final UUID id;
    private final String content;
    private final SenderRole senderRole;
    private final LocalDateTime sentAt;

    public Message(String content, SenderRole senderRole) {
        this(UUID.randomUUID(), content, senderRole, LocalDateTime.now());
    }

    public Message(UUID id, String content, SenderRole senderRole, LocalDateTime sentAt) {
        this.id = Objects.requireNonNull(id, "The message id is required");
        this.content = validateContent(content);
        this.senderRole = Objects.requireNonNull(senderRole, "The sender role is required");
        this.sentAt = Objects.requireNonNull(sentAt, "The sent date is required");
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
