package BC2_SeguimientoEmocional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementacion en memoria del repositorio de bitacoras emocionales.
 *
 * <p>Estilo de programacion aplicado: <b>Persistent-Tables</b>. Los datos se
 * modelan como una "tabla" ({@code tablaBitacoras}: estudianteId -&gt; bitacora),
 * similar a un esquema relacional donde el resto de datos (registros) cuelgan
 * de la fila principal. Las consultas no se resuelven iterando manualmente
 * con indices, sino de forma declarativa con la Stream API, que actua como
 * el motor de consultas del estilo.</p>
 *
 * <p>Nota: esta clase no formaba parte de los archivos originales; se agrega
 * en el mismo paquete (sin carpetas nuevas) porque la interfaz
 * {@code BitacoraEmocionalRepository} necesita una implementacion concreta
 * para poder demostrar el estilo Persistent-Tables.</p>
 */
public class BitacoraEmocionalRepositoryImpl implements BitacoraEmocionalRepository {

    // "Tabla" bitacoras: estudianteId -> bitacora
    private final Map<UUID, BitacoraEmocional> tablaBitacoras = new HashMap<>();

    @Override
    public void guardar(BitacoraEmocional bitacora) {
        Objects.requireNonNull(bitacora, "La bitacora a guardar no puede ser nula");
        tablaBitacoras.put(bitacora.getEstudianteId(), bitacora);
    }

    @Override
    public Optional<BitacoraEmocional> buscarPorEstudiante(UUID estudianteId) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        return Optional.ofNullable(tablaBitacoras.get(estudianteId));
    }

    @Override
    public List<Emocion> buscarPorRangoFecha(UUID estudianteId, LocalDate desde, LocalDate hasta) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        Objects.requireNonNull(desde, "La fecha 'desde' es obligatoria");
        Objects.requireNonNull(hasta, "La fecha 'hasta' es obligatoria");

        return buscarPorEstudiante(estudianteId)
            .map(BitacoraEmocional::getRegistros)
            .orElseGet(ArrayList::new)
            .stream()
            .filter(emocion -> dentroDeRango(emocion, desde, hasta))
            .collect(Collectors.toList());
    }

    private static boolean dentroDeRango(Emocion emocion, LocalDate desde, LocalDate hasta) {
        LocalDate fecha = emocion.getFecha();
        return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
    }
}
