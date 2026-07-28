package presentation.BC3_SupportChat;

import BC3_SoporteChat.Message;
import BC3_SoporteChat.SenderRole;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageResponse {

    private final UUID id;
    private final String content;
    private final SenderRole senderRole;
    private final LocalDateTime sentAt;

    public MessageResponse(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.senderRole = message.getSenderRole();
        this.sentAt = message.getTimestamp();
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
