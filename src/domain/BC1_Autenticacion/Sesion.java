package BC1_Autenticacion;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa una sesión de usuario activa en el sistema.
 * Es un elemento clave del dominio dentro del contexto de autenticación.
 */
public class Sesion {

    /**
     * Identificador único de la sesión (UUID).
     */
    private final UUID id;

    /**
     * Código universitario único del usuario que inició la sesión.
     */
    private final String codigoUsuario;

    /**
     * Fecha y hora en la que se inició la sesión.
     */
    private final LocalDateTime fechaInicio;

    /**
     * Fecha y hora en la que finalizó la sesión (null si sigue activa).
     */
    private LocalDateTime fechaFin;

    /**
     * Estado que indica si la sesión se encuentra activa o no.
     */
    private boolean activa;

    /**
     * Constructor para inicializar una nueva sesión activa con validación básica.
     *
     * @param codigoUsuario Código universitario del usuario.
     * @throws IllegalArgumentException si el código de usuario es nulo o vacío.
     */
    public Sesion(String codigoUsuario) {
        if (codigoUsuario == null || codigoUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de usuario no puede ser nulo o vacío.");
        }
        this.id = UUID.randomUUID();
        this.codigoUsuario = codigoUsuario;
        this.fechaInicio = LocalDateTime.now();
        this.activa = true;
    }

    /**
     * Cierra la sesión actual actualizando la fecha de fin y el estado a inactiva.
     */
    public void desactivar() {
        this.activa = false;
        this.fechaFin = LocalDateTime.now();
    }

    /**
     * Obtiene el identificador único de la sesión.
     *
     * @return El UUID identificador de la sesión.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Obtiene el código de usuario asociado a la sesión.
     *
     * @return El código universitario del usuario.
     */
    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * Obtiene la fecha y hora de inicio de la sesión.
     *
     * @return LocalDateTime con el momento de inicio.
     */
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Obtiene la fecha y hora de finalización de la sesión.
     *
     * @return LocalDateTime con el momento de cierre, o null si la sesión sigue activa.
     */
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    /**
     * Indica si la sesión se encuentra actualmente activa.
     *
     * @return true si la sesión está activa, false en caso contrario.
     */
    public boolean isActiva() {
        return activa;
    }
}