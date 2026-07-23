package domain;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface RecomendacionRepository {

    /**
     * @return
     */
    public List<Recomendacion> listarTodas();

    /**
     * @param tipo 
     * @return
     */
    public List<Recomendacion> buscarPorTipo(String tipo);

    /**
     * 
     */
    public void listarPorTipo(tipo: String): List<Recomendacion>();

    /**
     * 
     */
    public void Operation1();

}