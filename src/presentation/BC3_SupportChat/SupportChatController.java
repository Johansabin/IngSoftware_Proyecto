package presentation.BC3_SupportChat;

import application.BC3_SupportChat.ChatSessionAppService;
import application.BC3_SupportChat.SendMessageRequest;
import application.BC3_SupportChat.StartChatRequest;
import BC3_SoporteChat.ChatSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/support-chats")
public class SupportChatController {

    private final ChatSessionAppService chatSessionAppService;

    public SupportChatController(ChatSessionAppService chatSessionAppService) {
        this.chatSessionAppService = chatSessionAppService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatSessionResponse startChat(@RequestBody StartChatHttpRequest request) {
        UUID chatId = chatSessionAppService.startChat(new StartChatRequest(request.getPsychologistId()));
        return getChat(chatId);
    }

    @PostMapping("/{chatId}/messages")
    public ChatSessionResponse sendMessage(
            @PathVariable UUID chatId,
            @RequestBody SendMessageHttpRequest request) {
        chatSessionAppService.sendMessage(new SendMessageRequest(
                chatId,
                request.getContent(),
                request.getSenderRole()));
        return getChat(chatId);
    }

    @PostMapping("/{chatId}/close")
    public ChatSessionResponse closeChat(@PathVariable UUID chatId) {
        chatSessionAppService.closeChat(chatId);
        return getChat(chatId);
    }

    @GetMapping("/{chatId}")
    public ChatSessionResponse getChat(@PathVariable UUID chatId) {
        return new ChatSessionResponse(chatSessionAppService.getChat(chatId));
    }

    @GetMapping("/psychologists/{psychologistId}/active")
    public List<ChatSessionResponse> getActiveChatsByPsychologist(@PathVariable UUID psychologistId) {
        return chatSessionAppService.getActiveChatsByPsychologist(psychologistId).stream()
                .map(ChatSessionResponse::new)
                .toList();
    }
}
