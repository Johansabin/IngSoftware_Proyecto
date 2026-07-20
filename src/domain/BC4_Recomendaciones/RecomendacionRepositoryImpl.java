package BC4_Recomendaciones;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class RecomendacionRepositoryImpl implements RecomendacionRepository {

    private final Map<UUID, Recomendacion> tabla = new LinkedHashMap<>();

    @Override
    public void guardar(Recomendacion recomendacion) {
        // Clean Code: Tratamiento de errores temprano (Fail-Fast)
        Objects.requireNonNull(recomendacion, "La recomendación a guardar no puede ser nula");
        tabla.put(recomendacion.getId(), recomendacion);
    }

    @Override
    public void eliminar(UUID id) {
        Objects.requireNonNull(id, "El id a eliminar no puede ser nulo");
        tabla.remove(id);
    }

    @Override
    public List<Recomendacion> listarTodas() {
        // Clean Code: Copia defensiva para proteger la estructura interna
        return new ArrayList<>(tabla.values());
    }

    @Override
    public List<Recomendacion> buscarPorTipo(String tipo) {
        // Clean Code: Estilo Pipeline legible y sin efectos secundarios
        return tabla.values().stream()
                .filter(fila -> fila.esDelTipo(tipo))
                .toList();
    }
}
