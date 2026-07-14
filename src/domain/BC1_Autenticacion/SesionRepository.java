package BC1_Autenticacion;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de interfaz para persistir y buscar sesiones en la base de datos.
 */
public interface SesionRepository {

    /**
     * Guarda el estado de una sesion.
     *
     * @param sesion Objeto sesion a guardar.
     */
    void guardar(Sesion sesion);

    /**
     * Busca una sesion por su identificador unico.
     *
     * @param id Identificador unico de la sesion.
     * @return Un contenedor Optional con la sesion si es encontrada.
     */
    Optional<Sesion> buscarPorId(UUID id);

    /**
     * Busca la sesion activa mas reciente asociada a un codigo de usuario.
     *
     * @param codigoUsuario Codigo universitario.
     * @return Un contenedor Optional con la sesion si existe y esta activa.
     */
    Optional<Sesion> buscarActivaPorCodigo(String codigoUsuario);
}