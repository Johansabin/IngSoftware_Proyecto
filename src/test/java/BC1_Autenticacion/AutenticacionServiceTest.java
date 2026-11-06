package BC1_Autenticacion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Servicio de Aplicación con Mocks - AutenticacionService")
class AutenticacionServiceTest {

    @Mock
    private SesionRepository sesionRepository;

    @InjectMocks
    private AutenticacionService autenticacionService;

    @Test
    @DisplayName("Debe iniciar sesión exitosamente cuando no existe sesión previa activa")
    void iniciarSesionSinSesionPreviaExitoso() {
        // Arrange
        CredencialesInstitucionales credenciales = new CredencialesInstitucionales("20214567", "hashValido123");
        DatosAuditoria auditoria = new DatosAuditoria("192.168.1.50");
        when(sesionRepository.buscarActivaPorCodigo("20214567")).thenReturn(Optional.empty());

        // Act
        Optional<Sesion> resultado = autenticacionService.iniciarSesion(credenciales, auditoria);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("20214567", resultado.get().getCodigoUsuario());
        assertTrue(resultado.get().isActiva());

        ArgumentCaptor<Sesion> captor = ArgumentCaptor.forClass(Sesion.class);
        verify(sesionRepository, times(1)).buscarActivaPorCodigo("20214567");
        verify(sesionRepository, times(1)).guardar(captor.capture());
        assertEquals("20214567", captor.getValue().getCodigoUsuario());
        assertTrue(captor.getValue().isActiva());
    }

    @Test
    @DisplayName("Debe desactivar la sesión previa si el usuario ya tenía una sesión activa al iniciar sesión")
    void iniciarSesionConSesionPreviaDesactivaLaAnterior() {
        // Arrange
        CredencialesInstitucionales credenciales = new CredencialesInstitucionales("20214567", "hashValido123");
        DatosAuditoria auditoria = new DatosAuditoria("192.168.1.50");
        Sesion sesionPrevia = new Sesion("20214567");
        assertTrue(sesionPrevia.isActiva());

        when(sesionRepository.buscarActivaPorCodigo("20214567")).thenReturn(Optional.of(sesionPrevia));

        // Act
        Optional<Sesion> resultado = autenticacionService.iniciarSesion(credenciales, auditoria);

        // Assert
        assertTrue(resultado.isPresent());
        assertFalse(sesionPrevia.isActiva(), "La sesión previa debe haber sido desactivada");
        assertNotNull(sesionPrevia.getFechaFin(), "La sesión previa debe tener fecha de fin");

        verify(sesionRepository, times(1)).buscarActivaPorCodigo("20214567");
        verify(sesionRepository, times(1)).guardar(any(Sesion.class));
    }

    @Test
    @DisplayName("Debe retornar Optional vacío si credenciales o auditoría son nulas sin consultar repositorio")
    void iniciarSesionConParametrosNulosRetornaVacio() {
        // Arrange
        CredencialesInstitucionales credenciales = new CredencialesInstitucionales("20214567", "hashValido123");
        DatosAuditoria auditoria = new DatosAuditoria("192.168.1.50");

        // Act
        Optional<Sesion> resNullCreds = autenticacionService.iniciarSesion(null, auditoria);
        Optional<Sesion> resNullAudit = autenticacionService.iniciarSesion(credenciales, null);

        // Assert
        assertTrue(resNullCreds.isEmpty());
        assertTrue(resNullAudit.isEmpty());
        verifyNoInteractions(sesionRepository);
    }

    @Test
    @DisplayName("Debe cerrar sesión activa, cambiar su estado a inactiva y persistirla en repositorio")
    void cerrarSesionActivaExitoso() {
        // Arrange
        Sesion sesion = new Sesion("20214567");
        assertTrue(sesion.isActiva());

        // Act
        autenticacionService.cerrarSesion(sesion);

        // Assert
        assertFalse(sesion.isActiva());
        assertNotNull(sesion.getFechaFin());
        verify(sesionRepository, times(1)).guardar(sesion);
    }

    @Test
    @DisplayName("No debe llamar a persistir en repositorio si la sesión ya estaba inactiva o es nula")
    void cerrarSesionInactivaONulaNoGuardaEnRepositorio() {
        // Arrange
        Sesion sesionInactiva = new Sesion("20214567");
        sesionInactiva.desactivar();

        // Act
        autenticacionService.cerrarSesion(sesionInactiva);
        autenticacionService.cerrarSesion(null);

        // Assert
        verify(sesionRepository, never()).guardar(any());
    }

    @Test
    @DisplayName("Debe filtrar con Pipeline Stream únicamente las sesiones activas del usuario especificado")
    void obtenerSesionesActivasPorUsuarioFiltraCorrectamente() {
        // Arrange
        Sesion s1ActivaUsuarioA = new Sesion("20214567");
        Sesion s2InactivaUsuarioA = new Sesion("20214567");
        s2InactivaUsuarioA.desactivar();
        Sesion s3ActivaUsuarioB = new Sesion("20219999");

        List<Sesion> todas = List.of(s1ActivaUsuarioA, s2InactivaUsuarioA, s3ActivaUsuarioB);

        // Act
        List<Sesion> activasUsuarioA = autenticacionService.obtenerSesionesActivasPorUsuario(todas, "20214567");

        // Assert
        assertEquals(1, activasUsuarioA.size());
        assertEquals(s1ActivaUsuarioA.getId(), activasUsuarioA.getFirst().getId());
        assertEquals("20214567", activasUsuarioA.getFirst().getCodigoUsuario());
    }

    @Test
    @DisplayName("Debe retornar lista vacía si la lista de sesiones o el código de usuario son nulos")
    void obtenerSesionesActivasConParametrosNulosRetornaVacio() {
        // Arrange & Act
        List<Sesion> resListaNula = autenticacionService.obtenerSesionesActivasPorUsuario(null, "20214567");
        List<Sesion> resCodigoNulo = autenticacionService.obtenerSesionesActivasPorUsuario(List.of(), null);

        // Assert
        assertTrue(resListaNula.isEmpty());
        assertTrue(resCodigoNulo.isEmpty());
    }
}
