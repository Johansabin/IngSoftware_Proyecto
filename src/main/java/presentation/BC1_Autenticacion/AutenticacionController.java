package presentation.BC1_Autenticacion;

import BC1_Autenticacion.AutenticacionService;
import BC1_Autenticacion.CredencialesInstitucionales;
import BC1_Autenticacion.DatosAuditoria;
import BC1_Autenticacion.Sesion;
import BC1_Autenticacion.SesionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AutenticacionController {

    private final AutenticacionService autenticacionService;
    private final SesionRepository sesionRepository;

    public AutenticacionController(AutenticacionService autenticacionService, SesionRepository sesionRepository) {
        this.autenticacionService = autenticacionService;
        this.sesionRepository = sesionRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Sesion> login(@RequestBody LoginRequest request) {
        if (request == null || request.getCodigoUniversitario() == null || request.getContrasena() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            CredencialesInstitucionales credenciales = new CredencialesInstitucionales(
                request.getCodigoUniversitario(),
                request.getContrasena()
            );
            if (!credenciales.esCodigoValido()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String ip = request.getIpOrigen() != null ? request.getIpOrigen() : "127.0.0.1";
            DatosAuditoria auditoria = new DatosAuditoria(ip);

            Optional<Sesion> sesionOpt = autenticacionService.iniciarSesion(credenciales, auditoria);
            if (sesionOpt.isPresent()) {
                return ResponseEntity.ok(sesionOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest request) {
        if (request == null || request.getSessionId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<Sesion> sesionOpt = sesionRepository.buscarPorId(request.getSessionId());
        if (sesionOpt.isPresent()) {
            autenticacionService.cerrarSesion(sesionOpt.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
