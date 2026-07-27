package domain;

import BC1_Autenticacion.Sesion;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface SesionRepository {

    /**
     * 
     */
    public void guardar(Sesion sesion);

    /**
     * @param id 
     * @return
     */
    public Sesion buscarPorId(UUID id);

    /**
     * 
     */
    public void actualizar(Sesion sesion);

    /**
     * 
     */
    public void Operation1();

}
