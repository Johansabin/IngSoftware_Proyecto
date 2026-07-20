package application.BC3_SupportChat;

import java.util.UUID;

public class ChatSessionNotFoundException extends RuntimeException {

    public ChatSessionNotFoundException(UUID chatId) {
        super("No existe una sesion de chat con el id indicado: " + chatId);
    }
}
