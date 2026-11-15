package presentation.BC3_SupportChat;

import BC3_SoporteChat.SenderRole;

public class SendMessageHttpRequest {

    private String content;
    private SenderRole senderRole;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SenderRole getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(SenderRole senderRole) {
        this.senderRole = senderRole;
    }
}
