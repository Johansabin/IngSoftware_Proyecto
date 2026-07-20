package application.BC3_SupportChat;

import BC3_SoporteChat.ChatSession;

import java.util.UUID;

public interface ChatSessionAppService {

    UUID startChat(StartChatRequest request);

    void sendMessage(SendMessageRequest request);

    void closeChat(UUID chatId);

    ChatSession getChat(UUID chatId);
}
