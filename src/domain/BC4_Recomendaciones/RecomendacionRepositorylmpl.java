package BC4_Recomendaciones;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementación del repositorio de {@code Recomendacion}.
 *
 * <p><b>Estilo: Persistent Tables.</b> Los datos se modelan como una tabla
 * indexada por su clave primaria ({@code id}), igual que en una base de
 * datos relacional: cada método es, conceptualmente, una operación de
 * tabla. Esta clase concentra toda la persistencia en memoria; el día que
 * el equipo conecte MySQL, solo esta clase cambia (JDBC/JPA) — el dominio
 * y el resto de capas no se enteran, porque dependen únicamente de la
 * interfaz {@link RecomendacionRepository}.</p>
 */

public class RecomendacionRepositoryImpl implements RecomendacionRepository {

    // La "tabla": clave primaria (id) -> fila (Recomendacion)
    private final Map<UUID, Recomendacion> tabla = new LinkedHashMap<>();

    @Override
    public void guardar(Recomendacion recomendacion) {
        // INSERT OR UPDATE tabla WHERE id = recomendacion.getId()
        Objects.requireNonNull(recomendacion, "La recomendación a guardar no puede ser nula");
        tabla.put(recomendacion.getId(), recomendacion);
    }

    @Override
    public void eliminar(UUID id) {
        // DELETE FROM tabla WHERE id = ?
        Objects.requireNonNull(id, "El id a eliminar no puede ser nulo");
        tabla.remove(id);
    }

    @Override
    public List<Recomendacion> listarTodas() {
        // SELECT * FROM tabla
        return new ArrayList<>(tabla.values());
    }

    @Override
    public List<Recomendacion> buscarPorTipo(String tipo) {
        // Estilo Pipeline: Datos que fluyen a través de una tubería de funciones
        return tabla.values().stream()
                .filter(fila -> fila.esDelTipo(tipo))
                .toList(); 
    }
}
