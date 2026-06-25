package BC3_SoporteChat;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface SesionChatRepository {

    /**
     * @param sesionChat
     */
    public void guardar(SesionChat sesionChat);

    /**
     * @param id
     */
    public void buscarPorId(UUID id);

    /**
     * @param psicologoId
     */
    public void buscarActivasPorPsicologo(UUID psicologoId);

}