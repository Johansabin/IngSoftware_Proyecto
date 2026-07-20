package BC1_Autenticacion;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de interfaz (Repositorio) para persistir, actualizar y buscar sesiones en el sistema.
 * Define el contrato para el acceso a datos del agregado Sesion según los principios de DDD.
 */
public interface SesionRepository {

    /**
     * Guarda o actualiza el estado de una sesión en el almacenamiento.
     *
     * @param sesion Objeto de tipo Sesion a persistir. No debe ser nulo.
     */
    void guardar(Sesion sesion);

    /**
     * Busca una sesión en el sistema a partir de su identificador único.
     *
     * @param id Identificador único (UUID) de la sesión. No debe ser nulo.
     * @return Un contenedor Optional con la sesión correspondiente, o un Optional vacío si no se encuentra.
     */
    Optional<Sesion> buscarPorId(UUID id);

    /**
     * Busca la sesión activa más reciente asociada a un código de usuario específico.
     *
     * @param codigoUsuario Código universitario del usuario. No debe ser nulo o vacío.
     * @return Un contenedor Optional con la sesión si existe y está activa, o un Optional vacío en caso contrario.
     */
    Optional<Sesion> buscarActivaPorCodigo(String codigoUsuario);
}