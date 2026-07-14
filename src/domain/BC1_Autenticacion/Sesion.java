package BC1_Autenticacion;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa una sesion de usuario activa en el sistema.
 */
public class Sesion {

    private final UUID id;
    private final String codigoUsuario;
    private final LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean activa;

    /**
     * Constructor para inicializar una nueva sesion activa.
     *
     * @param codigoUsuario Codigo universitario del usuario.
     */
    public Sesion(String codigoUsuario) {
        this.id = UUID.randomUUID();
        this.codigoUsuario = codigoUsuario;
        this.fechaInicio = LocalDateTime.now();
        this.activa = true;
    }

    /**
     * Cierra la sesion actual actualizando la fecha de fin y el estado.
     */
    public void desactivar() {
        this.activa = false;
        this.fechaFin = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public boolean isActiva() {
        return activa;
    }
}