package BC1_Autenticacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacionServiceValidacionesTest {

    private SesionRepository repo;
    private AutenticacionService service;

    @BeforeEach
    void setUp() {
        repo=mock(SesionRepository.class);
        service=new AutenticacionService(repo);
    }

    @Test
    void iniciarSesionConCredencialesNulasRetornaVacio() {
        DatosAuditoria auditoria=new DatosAuditoria("127.0.0.1");
        Optional<Sesion> resultado=service.iniciarSesion(null,auditoria);
        assertTrue(resultado.isEmpty());
        verifyNoInteractions(repo);
    }

    @Test
    void iniciarSesionConAuditoriaNulaRetornaVacio() {
        CredencialesInstitucionales c=new CredencialesInstitucionales("20210001","hash");
        Optional<Sesion> resultado=service.iniciarSesion(c,null);
        assertTrue(resultado.isEmpty());
        verifyNoInteractions(repo);
    }

    @Test
    void iniciarSesionCreaYGuardaSesionNueva() {
        CredencialesInstitucionales c=new CredencialesInstitucionales("20210001","hash");
        when(repo.buscarActivaPorCodigo("20210001")).thenReturn(Optional.empty());
        Optional<Sesion> resultado=service.iniciarSesion(c,new DatosAuditoria("127.0.0.1"));
        assertTrue(resultado.isPresent());
        assertEquals("20210001",resultado.get().getCodigoUsuario());
        verify(repo).guardar(resultado.get());
    }

    @Test
    void iniciarSesionDesactivaSesionPrevia() {
        Sesion previa=new Sesion("20210001");
        CredencialesInstitucionales c=new CredencialesInstitucionales("20210001","hash");
        when(repo.buscarActivaPorCodigo("20210001")).thenReturn(Optional.of(previa));
        Optional<Sesion> resultado=service.iniciarSesion(c,new DatosAuditoria("127.0.0.1"));
        assertFalse(previa.isActiva());
        assertTrue(resultado.isPresent());
    }

    @Test
    void cerrarSesionNulaNoInteractuaConRepositorio() {
        service.cerrarSesion(null);
        verifyNoInteractions(repo);
    }

    @Test
    void cerrarSesionYaInactivaNoGuarda() {
        Sesion s=new Sesion("20210001");
        s.desactivar();
        service.cerrarSesion(s);
        verifyNoInteractions(repo);
    }

    @Test
    void cerrarSesionActivaLaDesactivaYGuarda() {
        Sesion s=new Sesion("20210001");
        service.cerrarSesion(s);
        assertFalse(s.isActiva());
        verify(repo).guardar(s);
    }

    @Test
    void obtenerSesionesConListaNulaRetornaVacio() {
        List<Sesion> resultado=service.obtenerSesionesActivasPorUsuario(null,"20210001");
        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerSesionesConCodigoNuloRetornaVacio() {
        List<Sesion> resultado=service.obtenerSesionesActivasPorUsuario(List.of(new Sesion("20210001")),null);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerSesionesFiltraPorActivaYUsuario() {
        Sesion activa=new Sesion("20210001");
        Sesion inactiva=new Sesion("20210001");
        inactiva.desactivar();
        Sesion otroUsuario=new Sesion("20210002");
        List<Sesion> resultado=service.obtenerSesionesActivasPorUsuario(List.of(activa,inactiva,otroUsuario),"20210001");
        assertEquals(1,resultado.size());
        assertSame(activa,resultado.get(0));
    }
}
