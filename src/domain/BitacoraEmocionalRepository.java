package domain;

import BC2_SeguimientoEmocional.BitacoraEmocional;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface BitacoraEmocionalRepository {

    /**
     * @param bitacora 
     * @return
     */
    public void adicionar(BitacoraEmocional bitacora);

    /**
     * @param bitacora 
     * @return
     */
    public void actualizar(BitacoraEmocional bitacora);

    /**
     * @param estudianteId 
     * @return
     */
    public BitacoraEmocional buscarPorEstudiante(UUID estudianteId);

    /**
     * 
     */
    public void Operation1();

}