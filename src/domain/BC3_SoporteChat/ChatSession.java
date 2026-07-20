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
        this.id = Objects.requireNonNull(id, "El id de la sesion de chat es obligatorio");
        this.student = Objects.requireNonNull(student, "El seudonimo del estudiante es obligatorio");
        this.psychologistId = Objects.requireNonNull(psychologistId, "El id del psicologo es obligatorio");
        this.messages = new ArrayList<>(Objects.requireNonNull(messages, "La lista de mensajes es obligatoria"));
        this.status = Objects.requireNonNull(status, "El estado de la sesion es obligatorio");
        this.auditData = Objects.requireNonNull(auditData, "Los datos de auditoria son obligatorios");
    }

    public void sendMessage(Message message) {
        if (!isActive()) {
            throw new IllegalStateException("No se pueden enviar mensajes en una sesion cerrada");
        }

        messages.add(Objects.requireNonNull(message, "El mensaje es obligatorio"));
        auditData.registerUpdate();
    }

    public void close() {
        if (!isActive()) {
            return;
        }

        this.status = ChatSessionStatus.CLOSED;
        auditData.registerUpdate();
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

    public AuditData getAuditData() {
        return auditData;
    }
}
