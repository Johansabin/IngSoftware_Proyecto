package BC4_Recomendaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import infrastructure.BC4_Recomendaciones.InMemoryRecomendacionRepository;

class RecomendacionServiceTest {

    private RecomendacionRepository repositorio;
    private RecomendacionService servicio;

    @BeforeEach
    void prepararEscenario() {
        repositorio = new InMemoryRecomendacionRepository();
        servicio = new RecomendacionService(repositorio);
    }

    @Test
    @DisplayName("HF.3.2 - devuelve solo actividades, ordenadas por titulo")
    void devuelveActividadesOrdenadas() {
        repositorio.guardar(RecomendacionFactory.crearActividad("Meditar", "10 minutos guiados"));
        repositorio.guardar(RecomendacionFactory.crearActividad("Caminata", "15 minutos al aire libre"));
        repositorio.guardar(RecomendacionFactory.crearConsejo("Respiracion", "Tecnica 4-7-8"));

        List<Recomendacion> actividades = servicio.actividadesSugeridas();

        assertEquals(2, actividades.size());
        assertEquals("Caminata", actividades.get(0).getTitulo());
        assertEquals("Meditar", actividades.get(1).getTitulo());
    }

    @Test
    @DisplayName("HF.3.1 - titulos por tipo en mayusculas y sin duplicados")
    void devuelveTitulosNormalizadosSinDuplicados() {
        repositorio.guardar(RecomendacionFactory.crearConsejo("Respiracion", "Tecnica 4-7-8"));
        repositorio.guardar(RecomendacionFactory.crearConsejo("respiracion", "Variante"));
        repositorio.guardar(RecomendacionFactory.crearConsejo("Diario", "Escribe 10 minutos"));

        List<String> titulos = servicio.titulosPorTipo("CONSEJO");

        assertEquals(2, titulos.size());
        assertTrue(titulos.contains("RESPIRACION"));
        assertTrue(titulos.contains("DIARIO"));
    }

    @Test
    @DisplayName("Devuelve lista vacia cuando no hay coincidencias")
    void devuelveListaVaciaSinCoincidencias() {
        assertTrue(servicio.actividadesSugeridas().isEmpty());
    }

    @Test
    @DisplayName("Rechaza construirse con repositorio nulo")
    void rechazaRepositorioNulo() {
        assertThrows(NullPointerException.class, () -> new RecomendacionService(null));
    }
}
