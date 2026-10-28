package BC4_Recomendaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Issue #15 - Pruebas unitarias de Dominio (invariantes y comportamiento) - Modulo 4. */
class RecomendacionTest {

    // ---------- Invariantes del constructor ----------

    @Test
    @DisplayName("Rechaza un titulo vacio lanzando excepcion de dominio")
    void rechazaTituloVacio() {
        // Arrange
        String titulo = "  ";

        // Act + Assert
        assertThrows(RecomendacionInvalidaException.class,
                () -> new Recomendacion(titulo, "contenido", "CONSEJO"));
    }

    @Test
    @DisplayName("Rechaza un titulo nulo")
    void rechazaTituloNulo() {
        // Arrange + Act
        RecomendacionInvalidaException ex = assertThrows(RecomendacionInvalidaException.class,
                () -> new Recomendacion(null, "contenido", "CONSEJO"));

        // Assert
        assertEquals("El titulo de la recomendacion no puede estar vacio", ex.getMessage());
    }

    @Test
    @DisplayName("Rechaza un contenido nulo lanzando excepcion de dominio")
    void rechazaContenidoNulo() {
        // Act + Assert
        assertThrows(RecomendacionInvalidaException.class,
                () -> new Recomendacion("Titulo", null, "CONSEJO"));
    }

    @Test
    @DisplayName("Rechaza un contenido vacio")
    void rechazaContenidoVacio() {
        // Act
        RecomendacionInvalidaException ex = assertThrows(RecomendacionInvalidaException.class,
                () -> new Recomendacion("Titulo", "", "CONSEJO"));

        // Assert
        assertEquals("El contenido de la recomendacion no puede estar vacio", ex.getMessage());
    }

    @Test
    @DisplayName("Rechaza un tipo vacio")
    void rechazaTipoVacio() {
        // Act
        RecomendacionInvalidaException ex = assertThrows(RecomendacionInvalidaException.class,
                () -> new Recomendacion("Titulo", "Contenido", "   "));

        // Assert
        assertEquals("El tipo de la recomendacion no puede estar vacio", ex.getMessage());
    }

    @Test
    @DisplayName("Rechaza un tipo nulo")
    void rechazaTipoNulo() {
        // Act + Assert
        assertThrows(RecomendacionInvalidaException.class,
                () -> new Recomendacion("Titulo", "Contenido", null));
    }

    @Test
    @DisplayName("Una recomendacion valida conserva sus datos y genera id y auditoria")
    void creaRecomendacionValida() {
        // Arrange + Act
        Recomendacion r = new Recomendacion("Caminata", "15 minutos", "ACTIVIDAD");

        // Assert
        assertEquals("Caminata", r.getTitulo());
        assertEquals("15 minutos", r.getContenido());
        assertEquals("ACTIVIDAD", r.getTipo());
        assertNotNull(r.getId());
        assertNotNull(r.getAuditoria());
    }

    @Test
    @DisplayName("Dos recomendaciones distintas tienen identificadores distintos")
    void generaIdsUnicos() {
        // Arrange + Act
        Recomendacion a = new Recomendacion("A", "x", "CONSEJO");
        Recomendacion b = new Recomendacion("A", "x", "CONSEJO");

        // Assert
        assertNotEquals(a.getId(), b.getId());
    }

    // ---------- Comportamiento ----------

    @Test
    @DisplayName("esDelTipo compara sin distinguir mayusculas")
    void comparaTipoSinDistinguirMayusculas() {
        // Arrange
        Recomendacion recomendacion = new Recomendacion("Caminata", "15 minutos", "ACTIVIDAD");

        // Act + Assert
        assertTrue(recomendacion.esDelTipo("actividad"));
        assertFalse(recomendacion.esDelTipo("CONSEJO"));
    }

    @Test
    @DisplayName("esDelTipo devuelve false cuando el tipo consultado es nulo")
    void esDelTipoConNuloEsFalso() {
        // Arrange
        Recomendacion recomendacion = new Recomendacion("Caminata", "15 minutos", "ACTIVIDAD");

        // Act + Assert
        assertFalse(recomendacion.esDelTipo(null));
    }

    @Test
    @DisplayName("actualizarContenido reemplaza titulo y contenido validos")
    void actualizaContenidoValido() {
        // Arrange
        Recomendacion recomendacion = new Recomendacion("Viejo", "Texto viejo", "CONSEJO");

        // Act
        recomendacion.actualizarContenido("Nuevo", "Texto nuevo");

        // Assert
        assertEquals("Nuevo", recomendacion.getTitulo());
        assertEquals("Texto nuevo", recomendacion.getContenido());
    }

    @Test
    @DisplayName("actualizarContenido rechaza un titulo invalido")
    void rechazaActualizacionConTituloInvalido() {
        // Arrange
        Recomendacion recomendacion = new Recomendacion("Titulo", "Contenido", "CONSEJO");

        // Act + Assert
        assertThrows(RecomendacionInvalidaException.class,
                () -> recomendacion.actualizarContenido("   ", "Texto"));
    }

    @Test
    @DisplayName("actualizarContenido rechaza un contenido invalido y no altera el estado")
    void rechazaActualizacionConContenidoInvalidoSinCambiarEstado() {
        // Arrange
        Recomendacion recomendacion = new Recomendacion("Titulo", "Contenido", "CONSEJO");

        // Act
        assertThrows(RecomendacionInvalidaException.class,
                () -> recomendacion.actualizarContenido("Otro titulo", null));

        // Assert
        assertEquals("Titulo", recomendacion.getTitulo());
        assertEquals("Contenido", recomendacion.getContenido());
    }

    // ---------- Fabrica ----------

    @Test
    @DisplayName("Factory.crearActividad fija el tipo ACTIVIDAD")
    void fabricaCreaActividad() {
        // Act
        Recomendacion r = RecomendacionFactory.crearActividad("Caminata", "15 minutos");

        // Assert
        assertEquals("ACTIVIDAD", r.getTipo());
    }

    @Test
    @DisplayName("Factory.crearConsejo fija el tipo CONSEJO")
    void fabricaCreaConsejo() {
        // Act
        Recomendacion r = RecomendacionFactory.crearConsejo("Respiracion", "Tecnica 4-7-8");

        // Assert
        assertEquals("CONSEJO", r.getTipo());
    }

    @Test
    @DisplayName("Factory.crear propaga la excepcion de dominio ante datos invalidos")
    void fabricaPropagaInvariantes() {
        // Act + Assert
        assertThrows(RecomendacionInvalidaException.class,
                () -> RecomendacionFactory.crear("", "Contenido", "CONSEJO"));
    }
}