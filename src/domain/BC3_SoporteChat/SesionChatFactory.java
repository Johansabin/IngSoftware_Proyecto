package BC3_SoporteChat;

import java.util.Objects;
import java.util.UUID;

public class SesionChatFactory {

    private SesionChatFactory() {
    }

    public static SesionChat iniciar(UUID psicologoId) {
        Objects.requireNonNull(psicologoId, "El id del psicologo es obligatorio");
        return new SesionChat(SeudonimoAnonimo.generar(), psicologoId);
    }

}
