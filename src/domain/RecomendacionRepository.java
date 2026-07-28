package domain;

import BC4_Recomendaciones.Recomendacion;

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
    public List<Recomendacion> listarPorTipo(String tipo);

    /**
     * 
     */
    public void Operation1();

}
