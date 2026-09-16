package presentation.BC4_Recomendaciones;

import application.BC4_Recomendaciones.CrearRecomendacionRequest;
import application.BC4_Recomendaciones.RecomendacionAppService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * API REST del bounded context de Recomendaciones.
 *
 * <p>Estilo de programacion: <b>RESTful</b>. Los recursos se identifican por
 * URI, el verbo HTTP expresa la operacion y cada peticion es autocontenida
 * (sin estado de sesion en el servidor).</p>
 */
@RestController
@RequestMapping("/api/recomendaciones")
public class RecomendacionController {

    private final RecomendacionAppService recomendacionAppService;

    public RecomendacionController(RecomendacionAppService recomendacionAppService) {
        this.recomendacionAppService = recomendacionAppService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecomendacionResponse crear(@RequestBody CrearRecomendacionHttpRequest request) {
        UUID id = recomendacionAppService.crearRecomendacion(new CrearRecomendacionRequest(
                request.getTitulo(),
                request.getContenido(),
                request.getTipo()));
        return obtener(id);
    }

    @GetMapping("/{id}")
    public RecomendacionResponse obtener(@PathVariable UUID id) {
        return new RecomendacionResponse(recomendacionAppService.obtenerRecomendacion(id));
    }

    @GetMapping
    public List<RecomendacionResponse> listar(@RequestParam(required = false) String tipo) {
        List<BC4_Recomendaciones.Recomendacion> recomendaciones = (tipo == null || tipo.isBlank())
                ? recomendacionAppService.listarRecomendaciones()
                : recomendacionAppService.buscarPorTipo(tipo);

        return recomendaciones.stream()
                .map(RecomendacionResponse::new)
                .toList();
    }

    @GetMapping("/actividades")
    public List<RecomendacionResponse> listarActividadesSugeridas() {
        return recomendacionAppService.listarActividadesSugeridas().stream()
                .map(RecomendacionResponse::new)
                .toList();
    }

    @GetMapping("/titulos")
    public List<String> listarTitulosPorTipo(@RequestParam String tipo) {
        return recomendacionAppService.listarTitulosPorTipo(tipo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable UUID id) {
        recomendacionAppService.eliminarRecomendacion(id);
    }
}
