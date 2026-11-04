package BC4_Recomendaciones;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Servicio de dominio de Recomendaciones.
 *
 * <p>Estilo de programacion: <b>Pipeline</b>. Las consultas se resuelven
 * encadenando transformaciones sobre el flujo que entrega el repositorio
 * ({@code map -> distinct -> sorted -> toList}), sin bucles imperativos ni
 * variables acumuladoras: cada paso recibe la salida del anterior, igual que
 * una tuberia de Unix.</p>
 */
public class RecomendacionService {

    private static final String TIPO_ACTIVIDAD = "ACTIVIDAD";

    private final RecomendacionRepository repositorio;

    public RecomendacionService(RecomendacionRepository repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio, "El repositorio de recomendaciones es obligatorio");
    }

    /**
     * HF.3.1 - Titulos de las recomendaciones de un tipo, normalizados a
     * mayusculas y sin duplicados.
     *
     * @param tipo tipo de recomendacion a consultar
     * @return titulos normalizados, sin repetir
     */
    public List<String> titulosPorTipo(String tipo) {
        return repositorio.buscarPorTipo(tipo).stream()
                .map(Recomendacion::getTitulo)
                .map(String::toUpperCase)
                .distinct()
                .toList();
    }

    /**
     * HF.3.2 - Actividades sugeridas ordenadas alfabeticamente por titulo.
     *
     * @return actividades sugeridas listas para mostrarse al estudiante
     */
    public List<Recomendacion> actividadesSugeridas() {
        return repositorio.buscarPorTipo(TIPO_ACTIVIDAD).stream()
                .sorted(Comparator.comparing(Recomendacion::getTitulo, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
