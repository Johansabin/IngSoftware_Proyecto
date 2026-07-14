package BC5_Notificaciones;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface RecordatorioRepository {

    /**
     * @param recordatorio
     */
    public void guardar(Recordatorio recordatorio);

    /**
     * 
     */
    public void buscarActivos();

    /**
     * @param estudianteId
     */
    public void buscarPorEstudiante(UUID estudianteId);

    /**
     * @param id
     */
    public void eliminar(UUID id);

}