package BC4_Recomendaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import infrastructure.BC4_Recomendaciones.InMemoryRecomendacionRepository;

class InMemoryRecomendacionRepositoryTest {

    private RecomendacionRepository repositorio;

    @BeforeEach
    void prepararEscenario() {
        repositorio = new InMemoryRecomendacionRepository();
    }

    @Test
    @DisplayName("Guarda y lista las recomendaciones almacenadas")
    void guardaYLista() {
        repositorio.guardar(RecomendacionFactory.crearActividad("Caminata", "15 minutos"));

        assertEquals(1, repositorio.listarTodas().size());
    }

    @Test
    @DisplayName("Guardar dos veces la misma recomendacion no la duplica")
    void noDuplicaPorClavePrimaria() {
        Recomendacion recomendacion = RecomendacionFactory.crearActividad("Caminata", "15 minutos");

        repositorio.guardar(recomendacion);
        repositorio.guardar(recomendacion);

        assertEquals(1, repositorio.listarTodas().size());
    }

    @Test
    @DisplayName("Elimina la recomendacion por su identificador")
    void eliminaPorId() {
        Recomendacion recomendacion = RecomendacionFactory.crearActividad("Caminata", "15 minutos");
        repositorio.guardar(recomendacion);

        repositorio.eliminar(recomendacion.getId());

        assertTrue(repositorio.listarTodas().isEmpty());
    }

    @Test
    @DisplayName("Rechaza guardar una recomendacion nula")
    void rechazaGuardarNulo() {
        assertThrows(NullPointerException.class, () -> repositorio.guardar(null));
    }
}
