package presentation.BC3_SupportChat;

import BC1_Autenticacion.CredencialesInstitucionales;
import BC1_Autenticacion.DatosAuditoria;
import BC1_Autenticacion.Sesion;
import BC1_Autenticacion.SesionFactory;
import BC2_SeguimientoEmocional.BitacoraEmocional;
import BC2_SeguimientoEmocional.Emocion;
import BC2_SeguimientoEmocional.EscalaEmocional;
import BC2_SeguimientoEmocional.ResumenSemanal;
import BC4_Recomendaciones.Recomendacion;
import BC5_Notificaciones.Recordatorio;
import BC6_PrivacidadSeguridad.Consentimiento;
import BC6_PrivacidadSeguridad.PoliticaPrivacidad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/modules")
public class MenteEnCasaModulesController {

    @GetMapping("/bc1-authentication")
    public Map<String, Object> authenticationDemo() {
        CredencialesInstitucionales credenciales = new CredencialesInstitucionales("20261234", "hash-demo");
        DatosAuditoria auditoria = new DatosAuditoria("127.0.0.1");
        Sesion sesion = SesionFactory.registrarSesion(credenciales.getCodigoUniversitario());

        return Map.of(
                "boundedContext", "BC1 - Autenticacion",
                "codigoValido", credenciales.esCodigoValido(),
                "sesionId", sesion.getId(),
                "codigoUsuario", sesion.getCodigoUsuario(),
                "activa", sesion.isActiva(),
                "ipOrigen", auditoria.getIpOrigen(),
                "fechaRegistro", auditoria.getFechaRegistro());
    }

    @GetMapping("/bc2-emotional-tracking")
    public Map<String, Object> emotionalTrackingDemo() {
        UUID estudianteId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        LocalDate hoy = LocalDate.now();
        BitacoraEmocional bitacora = new BitacoraEmocional(estudianteId);
        bitacora.agregarRegistro(new Emocion(hoy, "estres", new EscalaEmocional(8), "Carga academica alta"));
        bitacora.agregarRegistro(new Emocion(hoy.minusDays(1), "calma", new EscalaEmocional(3), "Descanso adecuado"));
        bitacora.calcularResumenSemanal(hoy);
        ResumenSemanal resumen = bitacora.obtenerResumen(hoy);

        return Map.of(
                "boundedContext", "BC2 - Seguimiento Emocional",
                "bitacoraId", bitacora.getId(),
                "estudianteId", bitacora.getEstudianteId(),
                "registros", bitacora.getRegistros().size(),
                "semanaInicio", resumen.getSemanaInicio(),
                "promedioEstres", resumen.getPromedioEstres(),
                "diasDePaz", resumen.getDiasDePaz());
    }

    @GetMapping("/bc4-recommendations")
    public Map<String, Object> recommendationsDemo() {
        List<Recomendacion> recomendaciones = List.of(
                new Recomendacion("Respiracion consciente", "Realizar una pausa breve de respiracion guiada.", "Bienestar"),
                new Recomendacion("Organizacion semanal", "Separar tareas academicas por prioridad.", "Habitos"));

        return Map.of(
                "boundedContext", "BC4 - Recomendaciones",
                "total", recomendaciones.size(),
                "recomendaciones", recomendaciones.stream()
                        .map(recomendacion -> Map.of(
                                "id", recomendacion.getId(),
                                "titulo", recomendacion.getTitulo(),
                                "contenido", recomendacion.getContenido(),
                                "tipo", recomendacion.getTipo()))
                        .toList());
    }

    @GetMapping("/bc5-notifications")
    public Map<String, Object> notificationsDemo() {
        UUID estudianteId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        Recordatorio recordatorio = new Recordatorio(
                estudianteId,
                "Registrar estado emocional del dia",
                LocalDateTime.now().plusHours(2));

        return Map.of(
                "boundedContext", "BC5 - Notificaciones",
                "recordatorioId", recordatorio.getId(),
                "estudianteId", recordatorio.getEstudianteId(),
                "mensaje", recordatorio.getMensaje(),
                "fechaProgramada", recordatorio.getFechaProgramada(),
                "activo", recordatorio.isActivo());
    }

    @GetMapping("/bc6-privacy-security")
    public Map<String, Object> privacySecurityDemo() {
        UUID estudianteId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        PoliticaPrivacidad politica = new PoliticaPrivacidad(
                "1.0",
                "Consentimiento para tratamiento de datos emocionales del estudiante.");
        Consentimiento consentimiento = new Consentimiento(estudianteId, true);
        politica.agregarConsentimiento(consentimiento);

        return Map.of(
                "boundedContext", "BC6 - Privacidad y Seguridad",
                "politicaId", politica.getId(),
                "version", politica.getVersion(),
                "consentimientos", politica.getConsentimientos().size(),
                "estudianteAcepto", politica.estaAceptadaPor(estudianteId),
                "fechaAceptacion", consentimiento.getFechaAceptacion());
    }
}
