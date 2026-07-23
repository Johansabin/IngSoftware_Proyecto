package domain;

import BC6_PrivacidadSeguridad.PoliticaPrivacidad;
import BC6_PrivacidadSeguridad.Consentimiento;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface PoliticaPrivacidadRepository {

    /**
     * @return
     */
    public PoliticaPrivacidad buscarVigente();

    /**
     * @param consentimiento 
     * @return
     */
    public void guardarConsentimiento(Consentimiento consentimiento);

    /**
     * 
     */
    public void Operation1();

}