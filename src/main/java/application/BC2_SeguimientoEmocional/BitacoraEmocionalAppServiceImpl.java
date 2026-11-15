package application.BC2_SeguimientoEmocional;

import BC2_SeguimientoEmocional.BitacoraEmocional;
import BC2_SeguimientoEmocional.BitacoraEmocionalFactory;
import BC2_SeguimientoEmocional.BitacoraEmocionalRepository;
import BC2_SeguimientoEmocional.Emocion;
import BC2_SeguimientoEmocional.ResumenSemanal;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementacion de los casos de uso de la bitacora emocional.
 *
 * <p>Delega las reglas al dominio ({@link BitacoraEmocional}) y la persistencia
 * al repositorio, que se recibe por su interfaz (inversion de dependencias).</p>
 */
public class BitacoraEmocionalAppServiceImpl implements BitacoraEmocionalAppService {

    private final BitacoraEmocionalRepository bitacoraRepository;

    public BitacoraEmocionalAppServiceImpl(BitacoraEmocionalRepository bitacoraRepository) {
        this.bitacoraRepository =
                Objects.requireNonNull(bitacoraRepository, "El repositorio de bitacoras es obligatorio");
    }

    @Override
    public void registrarEmocion(UUID estudianteId, Emocion emocion) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        Objects.requireNonNull(emocion, "La emocion a registrar es obligatoria");

        BitacoraEmocional bitacora = bitacoraRepository.buscarPorEstudiante(estudianteId)
                .orElseGet(() -> BitacoraEmocionalFactory.crearParaEstudiante(estudianteId));
        bitacora.agregarRegistro(emocion);
        bitacoraRepository.guardar(bitacora);
    }

    @Override
    public ResumenSemanal obtenerResumenSemanal(UUID estudianteId, LocalDate semana) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        Objects.requireNonNull(semana, "La semana es obligatoria");

        BitacoraEmocional bitacora = bitacoraRepository.buscarPorEstudiante(estudianteId)
                .orElseThrow(() -> new BitacoraNotFoundException(estudianteId));
        bitacora.calcularResumenSemanal(semana);
        bitacoraRepository.guardar(bitacora);
        return bitacora.obtenerResumen(semana);
    }

    @Override
    public List<Emocion> obtenerHistorial(UUID estudianteId) {
        Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");

        return bitacoraRepository.buscarPorEstudiante(estudianteId)
                .map(BitacoraEmocional::getRegistros)
                .orElseGet(List::of);
    }
}
