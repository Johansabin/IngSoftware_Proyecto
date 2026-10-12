package presentation.BC1_Autenticacion;

import BC1_Autenticacion.AutenticacionService;
import BC1_Autenticacion.CredencialesInstitucionales;
import BC1_Autenticacion.DatosAuditoria;
import BC1_Autenticacion.Sesion;
import BC1_Autenticacion.SesionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.context.ContextConfiguration;

@WebMvcTest(AutenticacionController.class)
class AutenticacionControllerTest {

    @SpringBootApplication
    static class TestConfig {}

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AutenticacionService autenticacionService;

    @MockBean
    private SesionRepository sesionRepository;

    @Test
    void testLoginExitoso() throws Exception {
        LoginRequest request = new LoginRequest("20214567", "Password123*", "127.0.0.1");
        Sesion sesion = new Sesion("20214567");

        when(autenticacionService.iniciarSesion(any(CredencialesInstitucionales.class), any(DatosAuditoria.class)))
            .thenReturn(Optional.of(sesion));

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.codigoUsuario").value("20214567"))
            .andExpect(jsonPath("$.activa").value(true));
    }

    @Test
    void testLoginCredencialesInvalidas() throws Exception {
        LoginRequest request = new LoginRequest("123", "pass", "127.0.0.1");

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginNoAutorizado() throws Exception {
        LoginRequest request = new LoginRequest("20214567", "WrongPass", "127.0.0.1");

        when(autenticacionService.iniciarSesion(any(CredencialesInstitucionales.class), any(DatosAuditoria.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogoutExitoso() throws Exception {
        UUID sessionId = UUID.randomUUID();
        LogoutRequest request = new LogoutRequest(sessionId, "20214567");
        Sesion sesion = new Sesion("20214567");

        when(sesionRepository.buscarPorId(sessionId)).thenReturn(Optional.of(sesion));

        mockMvc.perform(post("/api/v1/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    void testLogoutSesionNoEncontrada() throws Exception {
        UUID sessionId = UUID.randomUUID();
        LogoutRequest request = new LogoutRequest(sessionId, "20214567");

        when(sesionRepository.buscarPorId(sessionId)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound());
    }
}
