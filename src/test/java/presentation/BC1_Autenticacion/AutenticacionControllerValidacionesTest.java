package presentation.BC1_Autenticacion;

import BC1_Autenticacion.AutenticacionService;
import BC1_Autenticacion.CredencialesInstitucionales;
import BC1_Autenticacion.DatosAuditoria;
import BC1_Autenticacion.Sesion;
import BC1_Autenticacion.SesionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AutenticacionControllerValidacionesTest {

    private AutenticacionService service;
    private SesionRepository repo;
    private AutenticacionController controller;

    @BeforeEach
    void setUp() {
        service=mock(AutenticacionService.class);
        repo=mock(SesionRepository.class);
        controller=new AutenticacionController(service,repo);
    }

    @Test
    void loginConRequestNuloRetorna400() {
        ResponseEntity<Sesion> respuesta=controller.login(null);
        assertEquals(400,respuesta.getStatusCode().value());
    }

    @Test
    void loginSinCodigoRetorna400() {
        ResponseEntity<Sesion> respuesta=controller.login(new LoginRequest(null,"pw","10.0.0.1"));
        assertEquals(400,respuesta.getStatusCode().value());
        verifyNoInteractions(service);
    }

    @Test
    void loginSinContrasenaRetorna400() {
        ResponseEntity<Sesion> respuesta=controller.login(new LoginRequest("20210001",null,"10.0.0.1"));
        assertEquals(400,respuesta.getStatusCode().value());
        verifyNoInteractions(service);
    }

    @Test
    void loginConContrasenaVaciaRetorna400() {
        ResponseEntity<Sesion> respuesta=controller.login(new LoginRequest("20210001","   ","10.0.0.1"));
        assertEquals(400,respuesta.getStatusCode().value());
        verifyNoInteractions(service);
    }

    @Test
    void loginConCodigoDeFormatoInvalidoRetorna400() {
        ResponseEntity<Sesion> respuesta=controller.login(new LoginRequest("abc123","pw","10.0.0.1"));
        assertEquals(400,respuesta.getStatusCode().value());
        verifyNoInteractions(service);
    }

    @Test
    void loginSinIpUsaIpPorDefectoYRetorna200() {
        Sesion sesion=new Sesion("20210001");
        when(service.iniciarSesion(any(),any())).thenReturn(Optional.of(sesion));
        ResponseEntity<Sesion> respuesta=controller.login(new LoginRequest("20210001","pw",null));
        assertEquals(200,respuesta.getStatusCode().value());
        verify(service).iniciarSesion(eq(new CredencialesInstitucionales("20210001","pw")),any(DatosAuditoria.class));
    }

    @Test
    void loginRechazadoPorServicioRetorna401() {
        when(service.iniciarSesion(any(),any())).thenReturn(Optional.empty());
        ResponseEntity<Sesion> respuesta=controller.login(new LoginRequest("20210001","pw","10.0.0.1"));
        assertEquals(401,respuesta.getStatusCode().value());
    }

    @Test
    void logoutConRequestNuloRetorna400() {
        ResponseEntity<Void> respuesta=controller.logout(null);
        assertEquals(400,respuesta.getStatusCode().value());
        verifyNoInteractions(repo);
    }

    @Test
    void logoutSinSessionIdRetorna400() {
        ResponseEntity<Void> respuesta=controller.logout(new LogoutRequest(null,"20210001"));
        assertEquals(400,respuesta.getStatusCode().value());
        verifyNoInteractions(repo);
    }
}
