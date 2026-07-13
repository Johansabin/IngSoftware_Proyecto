package BC4_Recomendaciones;

import java.util.List;
import java.util.UUID;

public interface RecomendacionRepository {

    void guardar(Recomendacion recomendacion);

    void eliminar(UUID id);

    List<Recomendacion> listarTodas();

    List<Recomendacion> buscarPorTipo(String tipo);
}
