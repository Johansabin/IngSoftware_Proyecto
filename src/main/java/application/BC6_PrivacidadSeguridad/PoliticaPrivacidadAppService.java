package application.BC6_PrivacidadSeguridad;

import BC6_PrivacidadSeguridad.Consentimiento;
import BC6_PrivacidadSeguridad.PoliticaPrivacidad;

import java.util.UUID;

/**
 * Casos de uso del bounded context de Privacidad y Seguridad.
 * Orquesta el dominio sin contener reglas de negocio.
 */
public interface PoliticaPrivacidadAppService {

    /**
     * @return la politica de privacidad vigente
     * @throws PoliticaPrivacidadNotFoundException si no hay ninguna registrada
     */
    PoliticaPrivacidad obtenerPoliticaVigente();

    /**
     * Registra el consentimiento (aceptado o rechazado) de un estudiante sobre
     * la politica vigente y persiste el cambio.
     *
     * @throws IllegalArgumentException si el consentimiento pertenece a otro estudiante
     * @throws PoliticaPrivacidadNotFoundException si no hay politica vigente
     */
    void registrarConsentimiento(UUID estudianteId, Consentimiento consentimiento);
}
