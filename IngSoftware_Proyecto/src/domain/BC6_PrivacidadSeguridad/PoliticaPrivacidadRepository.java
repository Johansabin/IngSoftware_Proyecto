package BC6_PrivacidadSeguridad;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface PoliticaPrivacidadRepository {

    /**
     * @param politica
     */
    public void guardar(PoliticaPrivacidad politica);

    /**
     * 
     */
    public void buscarVigente();

    /**
     * @param version
     */
    public void buscarPorVersion(String version);

}