package application.BC4_Recomendaciones;

import BC4_Recomendaciones.Recomendacion;

import java.util.List;
import java.util.UUID;

/**
 * Casos de uso del bounded context de Recomendaciones.
 * Orquesta el dominio sin contener reglas de negocio.
 */
public interface RecomendacionAppService {

    UUID crearRecomendacion(CrearRecomendacionRequest request);

    Recomendacion obtenerRecomendacion(UUID id);

    List<Recomendacion> listarRecomendaciones();

    List<Recomendacion> buscarPorTipo(String tipo);

    List<Recomendacion> listarActividadesSugeridas();

    List<String> listarTitulosPorTipo(String tipo);

    void eliminarRecomendacion(UUID id);
}
