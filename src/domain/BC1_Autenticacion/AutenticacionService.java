package BC1_Autenticacion;

import java.util.Optional;

/**
 * Servicio de Dominio encargado de controlar el proceso de inicio y cierre de sesion.
 */
public class AutenticacionService {

    private final SesionRepository sesionRepository;

    /**
     * Constructor que inyecta la interfaz del repositorio.
     *
     * @param sesionRepository Repositorio de sesiones del sistema.
     */
    public AutenticacionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    /**
     * Realiza el login de un usuario en el dominio.
     *
     * @param credenciales Las credenciales que el usuario provee.
     * @param auditoria Datos adicionales de auditoria.
     * @return La sesion creada si el proceso es exitoso, vacio en caso contrario.
     */
    public Optional<Sesion> iniciarSesion(CredencialesInstitucionales credenciales, DatosAuditoria auditoria) {
        if (credenciales == null || auditoria == null) {
            return Optional.empty();
        }
        if (credenciales.getCodigoUniversitario() == null || credenciales.getCodigoUniversitario().isEmpty()) {
            return Optional.empty();
        }

        Optional<Sesion> sesionPrevia = sesionRepository.buscarActivaPorCodigo(credenciales.getCodigoUniversitario());
        sesionPrevia.ifPresent(Sesion::desactivar);

        Sesion nuevaSesion = SesionFactory.registrarSesion(credenciales.getCodigoUniversitario());
        sesionRepository.guardar(nuevaSesion);

        return Optional.of(nuevaSesion);
    }

    /**
     * Cierra la sesion indicada.
     *
     * @param sesion La sesion que desea cerrarse.
     */
    public void cerrarSesion(Sesion sesion) {
        if (sesion != null && sesion.isActiva()) {
            sesion.desactivar();
            sesionRepository.guardar(sesion);
        }
    }

    /**
     * Aplica el estilo Pipeline (Stream API) para filtrar sesiones activas.
     *
     * @param listaSesiones Lista general de sesiones.
     * @param codigoUsuario Código de usuario a buscar.
     * @return Lista filtrada de sesiones activas del usuario.
     */
    public java.util.List<Sesion> obtenerSesionesActivasPorUsuario(java.util.List<Sesion> listaSesiones, String codigoUsuario) {
        if (listaSesiones == null || codigoUsuario == null) {
            return java.util.Collections.emptyList();
        }
        return listaSesiones.stream()
                .filter(Sesion::isActiva)
                .filter(s -> s.getCodigoUsuario().equals(codigoUsuario))
                .collect(java.util.stream.Collectors.toList());
    }


}