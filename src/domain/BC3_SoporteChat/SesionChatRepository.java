package BC3_SoporteChat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SesionChatRepository {

    void guardar(SesionChat sesionChat);

    Optional<SesionChat> buscarPorId(UUID id);

    List<SesionChat> buscarActivasPorPsicologo(UUID psicologoId);

}
