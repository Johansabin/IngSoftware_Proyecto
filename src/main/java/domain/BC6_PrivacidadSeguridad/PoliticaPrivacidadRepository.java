package BC6_PrivacidadSeguridad;

import java.util.Optional;

public interface PoliticaPrivacidadRepository {
    void guardar(PoliticaPrivacidad politica);
    Optional<PoliticaPrivacidad> buscarVigente();
    Optional<PoliticaPrivacidad> buscarPorVersion(String version);
}
