package BC6_PrivacidadSeguridad;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class PoliticaPrivacidad {

    /**
     * Default constructor
     */
    public PoliticaPrivacidad() {
    }

    /**
     * 
     */
    private UUID id;

    /**
     * 
     */
    private String version;

    /**
     * 
     */
    private String contenido;

    /**
     * 
     */
    private List<Consentimiento> consentimientos;

    /**
     * 
     */
    private DatosAuditoria auditoria;

    /**
     * @param consentimiento
     */
    public void agregarConsentimiento(Consentimiento consentimiento) {
        // TODO implement here
    }

    /**
     * @param estudianteId
     */
    public void estaAceptadaPor(UUID estudianteId) {
        // TODO implement here
    }

}