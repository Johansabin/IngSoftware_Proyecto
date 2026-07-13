package BC3_SoporteChat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatSession {
    private UUID id;
    private AnonymousPseudonym student;
    private UUID psychologistId;
    private List<Message> messages;
    private ChatSessionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;

    public ChatSession() {
        this.messages = new ArrayList<>();
        this.status = ChatSessionStatus.ACTIVE;
    }

    public ChatSession(AnonymousPseudonym student, UUID psychologistId) {
        this.id = UUID.randomUUID();
        this.student = Objects.requireNonNull(student, "The anonymous student is required");
        this.psychologistId = Objects.requireNonNull(psychologistId, "The psychologist is required");
        this.messages = new ArrayList<>();
        this.status = ChatSessionStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public void sendMessage(Message message) {
        Objects.requireNonNull(message, "The message is required");

        if (status == ChatSessionStatus.CLOSED) {
            throw new IllegalStateException("Cannot send messages to a closed chat session");
        }

        messages.add(message);
    }

    public void close() {
        if (status == ChatSessionStatus.CLOSED) {
            return;
        }

        status = ChatSessionStatus.CLOSED;
        closedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return status == ChatSessionStatus.ACTIVE;
    }

    public UUID getId() {
        return id;
    }

    public AnonymousPseudonym getStudent() {
        return student;
    }

    public UUID getPsychologistId() {
        return psychologistId;
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public ChatSessionStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }
}

