package BC1_Autenticacion;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface SesionRepository {

    /**
     * @param sesion
     */
    public void guardar(Sesion sesion);

    /**
     * @param id
     */
    public void buscarPorId(UUID id);

    /**
     * @param codigoUniversitario
     */
    public void buscarActiva(String codigoUniversitario);

    /**
     * @param id
     */
    public void eliminar(UUID id);

}