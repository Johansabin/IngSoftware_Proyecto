package presentation.BC3_SupportChat;

import application.BC3_SupportChat.ChatSessionAppService;
import application.BC3_SupportChat.SendMessageRequest;
import application.BC3_SupportChat.StartChatRequest;
import BC3_SoporteChat.ChatSession;
import BC3_SoporteChat.SenderRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupportChatDemoRunner implements CommandLineRunner {

    private final ChatSessionAppService chatSessionAppService;

    public SupportChatDemoRunner(ChatSessionAppService chatSessionAppService) {
        this.chatSessionAppService = chatSessionAppService;
    }

    @Override
    public void run(String... args) {
        UUID psychologistId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID chatId = chatSessionAppService.startChat(new StartChatRequest(psychologistId));

        chatSessionAppService.sendMessage(new SendMessageRequest(
                chatId,
                "Necesito hablar con alguien sobre mi estres academico.",
                SenderRole.STUDENT));

        ChatSession chatSession = chatSessionAppService.getChat(chatId);
        System.out.println("BC3 Support Chat running");
        System.out.println("Demo chat id: " + chatSession.getId());
        System.out.println("Demo psychologist id: " + chatSession.getPsychologistId());
        System.out.println("Demo messages: " + chatSession.getMessages().size());
    }
}
