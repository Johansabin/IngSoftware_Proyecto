package application.BC6_PrivacidadSeguridad;

import BC6_PrivacidadSeguridad.Consentimiento;
import BC6_PrivacidadSeguridad.PoliticaPrivacidad;
import BC6_PrivacidadSeguridad.PoliticaPrivacidadRepository;

import java.util.Objects;
import java.util.UUID;

/**
 * Implementacion de los casos de uso de privacidad. Delega las reglas al
 * dominio ({@link PoliticaPrivacidad}) y la persistencia al repositorio, que
 * se recibe por su interfaz (inversion de dependencias).
 */
public class PoliticaPrivacidadAppServiceImpl implements PoliticaPrivacidadAppService {

    private final PoliticaPrivacidadRepository politicaRepository;

    public PoliticaPrivacidadAppServiceImpl(PoliticaPrivacidadRepository politicaRepository) {
        this.politicaRepository =
                Objects.requireNonNull(politicaRepository, "El repositorio de politicas es obligatorio");
    }

    @Override
    public PoliticaPrivacidad obtenerPoliticaVigente() {
        return politicaRepository.buscarVigente()
                .orElseThrow(PoliticaPrivacidadNotFoundException::new);
    }

    @Override
    public void registrarConsentimiento(UUID estudianteId, Consentimiento consentimiento) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        Objects.requireNonNull(consentimiento, "El consentimiento es obligatorio");
        if (!estudianteId.equals(consentimiento.getEstudianteId())) {
            throw new IllegalArgumentException("El consentimiento pertenece a otro estudiante");
        }

        PoliticaPrivacidad politica = obtenerPoliticaVigente();
        politica.agregarConsentimiento(consentimiento);
        politicaRepository.guardar(politica);
    }
}
