package domain;

import BC3_SoporteChat.SesionChat;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface SesionChatRepository {

    /**
     * @param sesionChat 
     * @return
     */
    public void adicionar(SesionChat sesionChat);

    /**
     * @param sesionChat 
     * @return
     */
    public void actualizar(SesionChat sesionChat);

    /**
     * @param id 
     * @return
     */
    public SesionChat buscar(UUID id);

}