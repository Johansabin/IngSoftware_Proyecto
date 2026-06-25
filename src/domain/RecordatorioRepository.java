package domain;

import BC5_Notificaciones.Recordatorio;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface RecordatorioRepository {

    /**
     * @param recordatorio 
     * @return
     */
    public void adicionar(Recordatorio recordatorio);

    /**
     * @param id 
     * @return
     */
    public void eliminar(UUID id);

    /**
     * @param id 
     * @return
     */
    public Recordatorio buscar(UUID id);

    /**
     * @param estudianteId 
     * @return
     */
    public List<Recordatorio> listarPorEstudiante(UUID estudianteId);

}