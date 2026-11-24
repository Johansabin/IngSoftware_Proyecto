package presentation.BC4_Recomendaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import BC4_Recomendaciones.RecomendacionRepository;
import application.BC4_Recomendaciones.RecomendacionAppService;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecomendacionControllerTest {

    @Test
    void flujoCompletoDeControllerYConfiguracion() {
        // Arrange
        RecomendacionesConfiguration config = new RecomendacionesConfiguration();
        RecomendacionRepository repository = config.recomendacionRepository();
        RecomendacionAppService appService = config.recomendacionAppService(repository);
        RecomendacionController controller = new RecomendacionController(appService);

        CrearRecomendacionHttpRequest req = new CrearRecomendacionHttpRequest();
        req.setTitulo("Pausa activa");
        req.setContenido("Estirar cuello y hombros 5 minutos");
        req.setTipo("Actividad");

        // Act
        RecomendacionResponse creada = controller.crear(req);
        List<RecomendacionResponse> todas = controller.listar(null);
        List<RecomendacionResponse> filtradas = controller.listar("Actividad");
        List<RecomendacionResponse> vaciasPorBlank = controller.listar("   ");
        List<RecomendacionResponse> actividades = controller.listarActividadesSugeridas();
        List<String> titulos = controller.listarTitulosPorTipo("Actividad");

        // Assert
        assertNotNull(creada.getId());
        assertEquals("Pausa activa", creada.getTitulo());
        assertEquals("Estirar cuello y hombros 5 minutos", creada.getContenido());
        assertEquals("Actividad", creada.getTipo());
        assertNotNull(creada.getCreadoEn());
        assertEquals(1, todas.size());
        assertEquals(1, filtradas.size());
        assertEquals(1, vaciasPorBlank.size());
        assertEquals(1, actividades.size());
        assertEquals(List.of("PAUSA ACTIVA"), titulos);

        // Act: eliminar
        controller.eliminar(creada.getId());

        // Assert
        assertTrue(controller.listar(null).isEmpty());
    }
}
