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
    public void guardar(sesion: Sesion)();

    /**
     * @param id 
     * @return
     */
    public Sesion buscarPorId(UUID id);

    /**
     * 
     */
    public void actualizar(sesion: Sesion)();

    /**
     * 
     */
    public void Operation1();

}