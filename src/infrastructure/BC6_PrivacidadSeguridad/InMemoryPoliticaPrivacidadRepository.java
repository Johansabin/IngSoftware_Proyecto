package infrastructure.BC6_PrivacidadSeguridad;

import BC6_PrivacidadSeguridad.PoliticaPrivacidad;
import BC6_PrivacidadSeguridad.PoliticaPrivacidadRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryPoliticaPrivacidadRepository implements PoliticaPrivacidadRepository {

    // Tabla version -> politica; el orden de insercion define cual es la vigente.
    private final Map<String, PoliticaPrivacidad> tabla = new LinkedHashMap<>();

    @Override
    public void guardar(PoliticaPrivacidad politica) {
        Objects.requireNonNull(politica, "La politica a guardar es obligatoria");
        tabla.remove(politica.getVersion());          // re-guardar la mueve al final
        tabla.put(politica.getVersion(), politica);
    }

    @Override
    public Optional<PoliticaPrivacidad> buscarVigente() {
        return tabla.values().stream().reduce((anterior, ultima) -> ultima);
    }

    @Override
    public Optional<PoliticaPrivacidad> buscarPorVersion(String version) {
        Objects.requireNonNull(version, "La version es obligatoria");
        return Optional.ofNullable(tabla.get(version));
    }
}