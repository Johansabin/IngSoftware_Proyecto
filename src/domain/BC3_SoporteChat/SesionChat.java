package BC3_SoporteChat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SesionChat {

    private final UUID id;
    private final SeudonimoAnonimo estudiante;
    private final UUID psicologoId;
    private final List<Mensaje> mensajes;
    private final DatosAuditoria auditoria;
    private EstadoSesionChat estado;

    public SesionChat(SeudonimoAnonimo estudiante, UUID psicologoId) {
        this(UUID.randomUUID(), estudiante, psicologoId, new ArrayList<>(), EstadoSesionChat.ACTIVA, new DatosAuditoria());
    }

    public SesionChat(
            UUID id,
            SeudonimoAnonimo estudiante,
            UUID psicologoId,
            List<Mensaje> mensajes,
            EstadoSesionChat estado,
            DatosAuditoria auditoria) {
        this.id = Objects.requireNonNull(id, "El id de la sesion de chat es obligatorio");
        this.estudiante = Objects.requireNonNull(estudiante, "El seudonimo del estudiante es obligatorio");
        this.psicologoId = Objects.requireNonNull(psicologoId, "El id del psicologo es obligatorio");
        this.mensajes = new ArrayList<>(Objects.requireNonNull(mensajes, "La lista de mensajes es obligatoria"));
        this.estado = Objects.requireNonNull(estado, "El estado de la sesion es obligatorio");
        this.auditoria = Objects.requireNonNull(auditoria, "Los datos de auditoria son obligatorios");
    }

    public void enviarMensaje(Mensaje mensaje) {
        if (!estaActiva()) {
            throw new IllegalStateException("No se pueden enviar mensajes en una sesion cerrada");
        }

        mensajes.add(Objects.requireNonNull(mensaje, "El mensaje es obligatorio"));
        auditoria.registrarActualizacion();
    }

    public void cerrar() {
        if (!estaActiva()) {
            return;
        }

        this.estado = EstadoSesionChat.CERRADA;
        auditoria.registrarActualizacion();
    }

    public boolean estaActiva() {
        return estado == EstadoSesionChat.ACTIVA;
    }

    public UUID getId() {
        return id;
    }

    public SeudonimoAnonimo getEstudiante() {
        return estudiante;
    }

    public UUID getPsicologoId() {
        return psicologoId;
    }

    public List<Mensaje> getMensajes() {
        return Collections.unmodifiableList(mensajes);
    }

    public EstadoSesionChat getEstado() {
        return estado;
    }

    public DatosAuditoria getAuditoria() {
        return auditoria;
    }

}
