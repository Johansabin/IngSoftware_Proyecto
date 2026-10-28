package application.BC4_Recomendaciones;

import BC4_Recomendaciones.Recomendacion;
import BC4_Recomendaciones.RecomendacionFactory;
import BC4_Recomendaciones.RecomendacionRepository;
import BC4_Recomendaciones.RecomendacionService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementacion de los casos de uso de recomendaciones.
 *
 * <p>Delega las reglas al dominio ({@link RecomendacionFactory} y
 * {@link RecomendacionService}) y la persistencia al repositorio.</p>
 */
public class RecomendacionAppServiceImpl implements RecomendacionAppService {

    private final RecomendacionRepository recomendacionRepository;
    private final RecomendacionService recomendacionService;

    public RecomendacionAppServiceImpl(RecomendacionRepository recomendacionRepository) {
        this.recomendacionRepository =
                Objects.requireNonNull(recomendacionRepository, "El repositorio de recomendaciones es obligatorio");
        this.recomendacionService = new RecomendacionService(recomendacionRepository);
    }

    @Override
    public UUID crearRecomendacion(CrearRecomendacionRequest request) {
        Objects.requireNonNull(request, "La solicitud de creacion es obligatoria");

        Recomendacion recomendacion =
                RecomendacionFactory.crear(request.getTitulo(), request.getContenido(), request.getTipo());
        recomendacionRepository.guardar(recomendacion);
        return recomendacion.getId();
    }

    @Override
    public Recomendacion obtenerRecomendacion(UUID id) {
        Objects.requireNonNull(id, "El id de la recomendacion es obligatorio");

        return recomendacionRepository.listarTodas().stream()
                .filter(recomendacion -> recomendacion.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RecomendacionNotFoundException(id));
    }

    @Override
    public List<Recomendacion> listarRecomendaciones() {
        return recomendacionRepository.listarTodas();
    }

    @Override
    public List<Recomendacion> buscarPorTipo(String tipo) {
        return recomendacionRepository.buscarPorTipo(tipo);
    }

    @Override
    public List<Recomendacion> listarActividadesSugeridas() {
        return recomendacionService.actividadesSugeridas();
    }

    @Override
    public List<String> listarTitulosPorTipo(String tipo) {
        return recomendacionService.titulosPorTipo(tipo);
    }

    @Override
    public void eliminarRecomendacion(UUID id) {
        Objects.requireNonNull(id, "El id de la recomendacion es obligatorio");
        recomendacionRepository.eliminar(id);
    }
}
