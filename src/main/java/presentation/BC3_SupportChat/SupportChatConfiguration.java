package presentation.BC3_SupportChat;

import application.BC3_SupportChat.ChatSessionAppService;
import application.BC3_SupportChat.ChatSessionAppServiceImpl;
import BC3_SoporteChat.ChatSessionRepository;
import infrastructure.BC3_SupportChat.InMemoryChatSessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupportChatConfiguration {

    @Bean
    public ChatSessionRepository chatSessionRepository() {
        return new InMemoryChatSessionRepository();
    }

    @Bean
    public ChatSessionAppService chatSessionAppService(ChatSessionRepository chatSessionRepository) {
        return new ChatSessionAppServiceImpl(chatSessionRepository);
    }
}
