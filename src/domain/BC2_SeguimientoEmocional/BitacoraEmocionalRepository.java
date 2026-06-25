package BC2_SeguimientoEmocional;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface BitacoraEmocionalRepository {

    /**
     * @param bitacora
     */
    public void guardar(BitacoraEmocional bitacora);

    /**
     * @param estudianteId
     */
    public void buscarPorEstudiante(UUID estudianteId);

    /**
     * @param estudianteId 
     * @param desde 
     * @param hasta
     */
    public void buscarPorRangoFecha(UUID estudianteId, LocalDate desde, LocalDate hasta);

}