package BC2_Seguimiento-Emocional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate root del bounded context Seguimiento Emocional: agrupa todos los
 * registros de emociones (Emocion) de un estudiante y calcula resúmenes sobre ellos.
 */
public final class BitacoraEmocional {

    private static final int DIAS_POR_SEMANA = 7;
    private static final String CATEGORIA_BAJO_ESTRES = "BAJO";

    private final UUID id;
    private final UUID estudianteId;
    private final List<Emocion> registros;
    private final DatosAuditoria auditoria;

    public BitacoraEmocional(UUID id, UUID estudianteId) {
        if (id == null || estudianteId == null) {
            throw new IllegalArgumentException("id y estudianteId son obligatorios");
        }
        this.id = id;
        this.estudianteId = estudianteId;
        this.registros = new ArrayList<>();
        this.auditoria = new DatosAuditoria();
    }

    /**
     * Agrega un nuevo registro emocional a la bitácora y actualiza la auditoría.
     *
     * @param emocion registro a agregar; no puede ser null
     */
    public void agregarRegistro(Emocion emocion) {
        if (emocion == null) {
            throw new IllegalArgumentException("La emoción a registrar no puede ser null");
        }
        registros.add(emocion);
        auditoria.actualizar();
    }

    /**
     * Calcula el resumen semanal a partir de los registros cuya fecha cae dentro
     * de los 7 días que empiezan en {@code semana} (inclusive).
     *
     * @param semana fecha de inicio de la semana a analizar
     * @return resumen con el promedio de estrés y los días de paz de esa semana
     */
    public ResumenSemanal calcularResumenSemanal(LocalDate semana) {
        if (semana == null) {
            throw new IllegalArgumentException("La semana a analizar no puede ser null");
        }
        LocalDate finSemana = semana.plusDays(DIAS_POR_SEMANA);

        List<Emocion> registrosDeLaSemana = new ArrayList<>();
        for (Emocion registro : registros) {
            LocalDate fecha = registro.getFecha();
            if (!fecha.isBefore(semana) && fecha.isBefore(finSemana)) {
                registrosDeLaSemana.add(registro);
            }
        }

        double promedioEstres = calcularPromedio(registrosDeLaSemana);
        int diasDePaz = contarDiasDePaz(registrosDeLaSemana);

        return new ResumenSemanal(promedioEstres, diasDePaz, semana);
    }

    private double calcularPromedio(List<Emocion> registrosDeLaSemana) {
        if (registrosDeLaSemana.isEmpty()) {
            return 0.0;
        }
        int suma = 0;
        for (Emocion registro : registrosDeLaSemana) {
            suma += registro.getEscala().getValor();
        }
        return (double) suma / registrosDeLaSemana.size();
    }

    private int contarDiasDePaz(List<Emocion> registrosDeLaSemana) {
        int contador = 0;
        for (Emocion registro : registrosDeLaSemana) {
            if (CATEGORIA_BAJO_ESTRES.equals(registro.getEscala().getCategoria())) {
                contador++;
            }
        }
        return contador;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    /** Devuelve una vista no modificable de los registros (no expone la lista interna). */
    public List<Emocion> getRegistros() {
        return Collections.unmodifiableList(registros);
    }

    public DatosAuditoria getAuditoria() {
        return auditoria;
    }
}