package infrastructure.BC1_Autenticacion;

import BC1_Autenticacion.Sesion;
import BC1_Autenticacion.SesionRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación en memoria del repositorio de sesiones para BC1 (Autenticación).
 * Sigue el patrón de persistencia en memoria establecido en la arquitectura del proyecto.
 */
public class InMemorySesionRepository implements SesionRepository {

    private final Map<UUID, Sesion> sesiones = new ConcurrentHashMap<>();

    @Override
    public void guardar(Sesion sesion) {
        Objects.requireNonNull(sesion, "La sesión no puede ser nula.");
        sesiones.put(sesion.getId(), sesion);
    }

    @Override
    public Optional<Sesion> buscarPorId(UUID id) {
        Objects.requireNonNull(id, "El ID de la sesión no puede ser nulo.");
        return Optional.ofNullable(sesiones.get(id));
    }

    @Override
    public Optional<Sesion> buscarActivaPorCodigo(String codigoUsuario) {
        if (codigoUsuario == null || codigoUsuario.isBlank()) {
            return Optional.empty();
        }
        return sesiones.values().stream()
                .filter(s -> s.isActiva() && codigoUsuario.equals(s.getCodigoUsuario()))
                .findFirst();
    }

    /**
     * Limpia todas las sesiones almacenadas en memoria (útil para TearDown en pruebas).
     */
    public void limpiar() {
        sesiones.clear();
    }

    /**
     * Devuelve la cantidad de sesiones actualmente almacenadas.
     *
     * @return Conteo total de sesiones.
     */
    public int contar() {
        return sesiones.size();
    }
}
