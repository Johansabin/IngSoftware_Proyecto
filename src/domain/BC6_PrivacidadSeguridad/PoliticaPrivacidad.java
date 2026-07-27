package BC6_PrivacidadSeguridad;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.*;

/**
 * 
 */
public class PoliticaPrivacidad {

    /**
     * Default constructor
     */
    public PoliticaPrivacidad() {
        this("1.0", "Politica vigente para consentimiento de uso de datos emocionales.");
    }

    public PoliticaPrivacidad(String version, String contenido) {
        this.id = UUID.randomUUID();
        this.version = Objects.requireNonNull(version, "La version de la politica es obligatoria");
        this.contenido = Objects.requireNonNull(contenido, "El contenido de la politica es obligatorio");
        this.consentimientos = new ArrayList<>();
        this.auditoria = new DatosAuditoria();
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
        consentimientos.add(Objects.requireNonNull(consentimiento, "El consentimiento es obligatorio"));
        auditoria.registrarActualizacion();
    }

    /**
     * @param estudianteId
     */
    public boolean estaAceptadaPor(UUID estudianteId) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        return consentimientos.stream()
                .anyMatch(consentimiento -> consentimiento.getEstudianteId().equals(estudianteId)
                        && consentimiento.isAceptado());
    }

    public UUID getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getContenido() {
        return contenido;
    }

    public List<Consentimiento> getConsentimientos() {
        return Collections.unmodifiableList(consentimientos);
    }

    public DatosAuditoria getAuditoria() {
        return auditoria;
    }

}
