package BC4_Recomendaciones;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface RecomendacionRepository {

    /**
     * @param recomendacion
     */
    public void guardar(Recomendacion recomendacion);

    /**
     * @param tipo
     */
    public void buscarPorTipo(String tipo);

    /**
     * 
     */
    public void listarTodas();

    /**
     * @param id
     */
    public void eliminar(UUID id);

}