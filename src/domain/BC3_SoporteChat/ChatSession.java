package BC3_SoporteChat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatSession {

    private final UUID id;
    private final AnonymousPseudonym student;
    private final UUID psychologistId;
    private final List<Message> messages;
    private final AuditData auditData;
    private ChatSessionStatus status;

    public ChatSession(AnonymousPseudonym student, UUID psychologistId) {
        this(UUID.randomUUID(), student, psychologistId, new ArrayList<>(), ChatSessionStatus.ACTIVE, new AuditData());
    }

    public ChatSession(
            UUID id,
            AnonymousPseudonym student,
            UUID psychologistId,
            List<Message> messages,
            ChatSessionStatus status,
            AuditData auditData) {
        this.id = Objects.requireNonNull(id, "The chat session id is required");
        this.student = Objects.requireNonNull(student, "The anonymous student is required");
        this.psychologistId = Objects.requireNonNull(psychologistId, "The psychologist is required");
        this.messages = new ArrayList<>(Objects.requireNonNull(messages, "The messages list is required"));
        this.status = Objects.requireNonNull(status, "The chat session status is required");
        this.auditData = Objects.requireNonNull(auditData, "The audit data is required");
    }

    public void sendMessage(Message message) {
        ensureActive();
        messages.add(Objects.requireNonNull(message, "The message is required"));
        auditData.registerUpdate();
    }

    public void close() {
        if (status == ChatSessionStatus.CLOSED) {
            return;
        }

        status = ChatSessionStatus.CLOSED;
        auditData.registerUpdate();
    }

    public boolean isActive() {
        return status == ChatSessionStatus.ACTIVE;
    }

    public boolean isAssignedTo(UUID psychologistId) {
        return this.psychologistId.equals(Objects.requireNonNull(psychologistId, "The psychologist is required"));
    }

    private void ensureActive() {
        if (status == ChatSessionStatus.CLOSED) {
            throw new IllegalStateException("Cannot send messages to a closed chat session");
        }
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

    public AuditData getAuditData() {
        return auditData;
    }
}
