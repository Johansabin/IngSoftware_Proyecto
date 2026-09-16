package infrastructure.BC4_Recomendaciones;

import BC4_Recomendaciones.Recomendacion;
import BC4_Recomendaciones.RecomendacionRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementacion en memoria del repositorio de recomendaciones, equivalente a
 * {@code InMemoryChatSessionRepository} en BC3.
 *
 * <p>Estilo de programacion: <b>Persistent Tables</b>. Los datos se modelan
 * como una tabla indexada por su clave primaria ({@code id}) y cada metodo
 * corresponde a una operacion relacional (INSERT/UPDATE, DELETE, SELECT). Al
 * migrar a MySQL solo cambia esta clase: el dominio depende unicamente de la
 * interfaz {@link RecomendacionRepository}.</p>
 */
public class InMemoryRecomendacionRepository implements RecomendacionRepository {

    /** La "tabla": clave primaria (id) -> fila (Recomendacion). */
    private final Map<UUID, Recomendacion> tabla = new LinkedHashMap<>();

    @Override
    public void guardar(Recomendacion recomendacion) {
        // INSERT OR UPDATE tabla WHERE id = ?
        Objects.requireNonNull(recomendacion, "La recomendacion a guardar es obligatoria");
        tabla.put(recomendacion.getId(), recomendacion);
    }

    @Override
    public void eliminar(UUID id) {
        // DELETE FROM tabla WHERE id = ?
        Objects.requireNonNull(id, "El id a eliminar es obligatorio");
        tabla.remove(id);
    }

    @Override
    public List<Recomendacion> listarTodas() {
        // SELECT * FROM tabla
        return new ArrayList<>(tabla.values());
    }

    @Override
    public List<Recomendacion> buscarPorTipo(String tipo) {
        // SELECT * FROM tabla WHERE tipo = ?
        return tabla.values().stream()
                .filter(fila -> fila.esDelTipo(tipo))
                .toList();
    }
}
