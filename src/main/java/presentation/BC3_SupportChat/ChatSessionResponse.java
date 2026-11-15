package presentation.BC3_SupportChat;

import BC3_SoporteChat.ChatSession;
import BC3_SoporteChat.ChatSessionStatus;

import java.util.List;
import java.util.UUID;

public class ChatSessionResponse {

    private final UUID id;
    private final String studentPseudonym;
    private final UUID psychologistId;
    private final ChatSessionStatus status;
    private final List<MessageResponse> messages;

    public ChatSessionResponse(ChatSession chatSession) {
        this.id = chatSession.getId();
        this.studentPseudonym = chatSession.getStudent().getAlias();
        this.psychologistId = chatSession.getPsychologistId();
        this.status = chatSession.getStatus();
        this.messages = chatSession.getMessages().stream()
                .map(MessageResponse::new)
                .toList();
    }

    public UUID getId() {
        return id;
    }

    public String getStudentPseudonym() {
        return studentPseudonym;
    }

    public UUID getPsychologistId() {
        return psychologistId;
    }

    public ChatSessionStatus getStatus() {
        return status;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }
}
