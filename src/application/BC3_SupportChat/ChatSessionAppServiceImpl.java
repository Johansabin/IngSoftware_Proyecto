package application.BC3_SupportChat;

import BC3_SoporteChat.Mensaje;
import BC3_SoporteChat.SesionChat;
import BC3_SoporteChat.SesionChatFactory;
import BC3_SoporteChat.SesionChatRepository;

import java.util.Objects;
import java.util.UUID;

public class ChatSessionAppServiceImpl implements ChatSessionAppService {

    private final SesionChatRepository repository;

    public ChatSessionAppServiceImpl(SesionChatRepository repository) {
        this.repository = Objects.requireNonNull(repository, "El repositorio de chat es obligatorio");
    }

    @Override
    public UUID startChat(StartChatRequest request) {
        Objects.requireNonNull(request, "La solicitud de inicio de chat es obligatoria");

        SesionChat chat = SesionChatFactory.iniciar(request.getPsychologistId());
        repository.guardar(chat);
        return chat.getId();
    }

    @Override
    public void sendMessage(SendMessageRequest request) {
        Objects.requireNonNull(request, "La solicitud de envio de mensaje es obligatoria");

        // Flujo de aplicacion: buscar sesion, crear mensaje, actualizar agregado y guardar.
        SesionChat chat = getChat(request.getChatId());
        Mensaje message = new Mensaje(request.getContent(), request.getSenderRole());
        chat.enviarMensaje(message);
        repository.guardar(chat);
    }

    @Override
    public void closeChat(UUID chatId) {
        SesionChat chat = getChat(chatId);
        chat.cerrar();
        repository.guardar(chat);
    }

    @Override
    public SesionChat getChat(UUID chatId) {
        Objects.requireNonNull(chatId, "El id del chat es obligatorio");

        return repository.buscarPorId(chatId)
                .orElseThrow(() -> new IllegalArgumentException("No existe una sesion de chat con el id indicado"));
    }
}
