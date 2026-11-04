package BC6_PrivacidadSeguridad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PoliticaPrivacidad {

    private final UUID id;
    private final String version;
    private final String contenido;
    private final List<Consentimiento> consentimientos;
    private final DatosAuditoria auditoria;

    public PoliticaPrivacidad(String version, String contenido) {
        this.id = UUID.randomUUID();
        this.version = validarTexto(version, "La version de la politica es obligatoria");
        this.contenido = validarTexto(contenido, "El contenido de la politica es obligatorio");
        this.consentimientos = new ArrayList<>();
        this.auditoria = new DatosAuditoria();
    }

    private static String validarTexto(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensaje);
        }
        return valor.trim();
    }

    public void agregarConsentimiento(Consentimiento consentimiento) {
        consentimientos.add(Objects.requireNonNull(consentimiento, "El consentimiento es obligatorio"));
        auditoria.registrarActualizacion();
    }

    public boolean estaAceptadaPor(UUID estudianteId) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        return consentimientos.stream()
                .anyMatch(c -> c.getEstudianteId().equals(estudianteId) && c.isAceptado());
    }

    public UUID getId() { return id; }
    public String getVersion() { return version; }
    public String getContenido() { return contenido; }
    public List<Consentimiento> getConsentimientos() { return Collections.unmodifiableList(consentimientos); }
    public DatosAuditoria getAuditoria() { return auditoria; }
}