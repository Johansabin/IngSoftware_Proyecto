package application.BC3_SupportChat;

import BC3_SoporteChat.SesionChat;

import java.util.UUID;

public interface ChatSessionAppService {

    UUID startChat(StartChatRequest request);

    void sendMessage(SendMessageRequest request);

    void closeChat(UUID chatId);

    SesionChat getChat(UUID chatId);
}
