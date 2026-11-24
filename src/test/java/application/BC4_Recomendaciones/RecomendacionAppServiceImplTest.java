package application.BC4_Recomendaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import BC4_Recomendaciones.Recomendacion;
import infrastructure.BC4_Recomendaciones.InMemoryRecomendacionRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecomendacionAppServiceImplTest {

    private static final String TIPO_ACTIVIDAD = "Actividad";

    private RecomendacionAppServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new RecomendacionAppServiceImpl(new InMemoryRecomendacionRepository());
    }

    @Test
    void crudYConsultasDeRecomendaciones() {
        // Arrange
        CrearRecomendacionRequest req1 = new CrearRecomendacionRequest(
                "Respiracion 4-7-8", "Inhala 4s, reten 7s, exhala 8s", TIPO_ACTIVIDAD);
        CrearRecomendacionRequest req2 = new CrearRecomendacionRequest(
                "Higiene del sueno", "Evitar pantallas antes de dormir", "Habito");

        // Act
        UUID id1 = service.crearRecomendacion(req1);
        UUID id2 = service.crearRecomendacion(req2);

        Recomendacion obtenida = service.obtenerRecomendacion(id1);
        List<Recomendacion> todas = service.listarRecomendaciones();
        List<Recomendacion> porTipo = service.buscarPorTipo("Habito");
        List<Recomendacion> actividades = service.listarActividadesSugeridas();
        List<String> titulos = service.listarTitulosPorTipo(TIPO_ACTIVIDAD);

        // Assert
        assertNotNull(obtenida);
        assertEquals("Respiracion 4-7-8", obtenida.getTitulo());
        assertEquals(2, todas.size());
        assertEquals(1, porTipo.size());
        assertEquals(1, actividades.size());
        assertEquals(List.of("RESPIRACION 4-7-8"), titulos);

        // Act: eliminar
        service.eliminarRecomendacion(id2);

        // Assert
        assertEquals(1, service.listarRecomendaciones().size());
    }

    @Test
    void obtenerInexistente_lanzaRecomendacionNotFoundException() {
        // Arrange
        UUID inexistente = UUID.randomUUID();

        // Act & Assert
        RecomendacionNotFoundException ex = assertThrows(
                RecomendacionNotFoundException.class,
                () -> service.obtenerRecomendacion(inexistente));
        assertTrue(ex.getMessage().contains(inexistente.toString()));
    }

    @Test
    void argumentosNulos_lanzanNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new RecomendacionAppServiceImpl(null));
        assertThrows(NullPointerException.class, () -> service.crearRecomendacion(null));
        assertThrows(NullPointerException.class, () -> service.obtenerRecomendacion(null));
        assertThrows(NullPointerException.class, () -> service.eliminarRecomendacion(null));
    }
}
