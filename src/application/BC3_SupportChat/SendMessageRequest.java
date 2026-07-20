package application.BC3_SupportChat;

import BC3_SoporteChat.SenderRole;

import java.util.Objects;
import java.util.UUID;

public class SendMessageRequest {

    private final UUID chatId;
    private final String content;
    private final SenderRole senderRole;

    public SendMessageRequest(UUID chatId, String content, SenderRole senderRole) {
        this.chatId = Objects.requireNonNull(chatId, "El id del chat es obligatorio");
        this.content = content;
        this.senderRole = Objects.requireNonNull(senderRole, "El rol del remitente es obligatorio");
    }

    public UUID getChatId() {
        return chatId;
    }

    public String getContent() {
        return content;
    }

    public SenderRole getSenderRole() {
        return senderRole;
    }
}
