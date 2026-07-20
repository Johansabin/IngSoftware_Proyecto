package BC2_SeguimientoEmocional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Bitacora emocional de un estudiante: acumula sus registros diarios y
 * calcula resumenes semanales.
 *
 * <p>Estilo de programacion aplicado: <b>Cookbook</b>. Cada operacion se
 * expresa como una "receta": una secuencia de procedimientos privados que
 * leen y modifican el estado compartido de la instancia
 * ({@code registros}, {@code registrosSemanaEnCalculo},
 * {@code promedioEstresEnCalculo}, {@code diasDePazEnCalculo}), en vez de
 * pasar y devolver datos explicitamente entre funciones puras. Los pasos
 * no tienen sentido fuera del orden en que se invocan.</p>
 */
public class BitacoraEmocional {

    private static final int DIAS_EN_SEMANA = 7;

    private final UUID id;
    private final UUID estudianteId;
    private final List<Emocion> registros;
    private final DatosAuditoria auditoria;
    private final Map<LocalDate, ResumenSemanal> resumenesSemanales;

    // Estado de trabajo compartido entre los pasos de la receta de calculo.
    private List<Emocion> registrosSemanaEnCalculo;
    private double promedioEstresEnCalculo;
    private int diasDePazEnCalculo;

    public BitacoraEmocional(UUID estudianteId) {
        this.id = UUID.randomUUID();
        this.estudianteId = Objects.requireNonNull(estudianteId, "El id del estudiante es obligatorio");
        this.registros = new ArrayList<>();
        this.auditoria = new DatosAuditoria();
        this.resumenesSemanales = new HashMap<>();
    }

    public UUID getId() {
        return id;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public List<Emocion> getRegistros() {
        return Collections.unmodifiableList(registros);
    }

    public DatosAuditoria getAuditoria() {
        return auditoria;
    }

    /**
     * Agrega un registro emocional a la bitacora.
     *
     * <p>Receta: validar -&gt; anadir al estado compartido -&gt; marcar auditoria.</p>
     */
    public void agregarRegistro(Emocion emocion) {
        Objects.requireNonNull(emocion, "La emocion a registrar no puede ser nula");
        registros.add(emocion);
        auditoria.marcarModificacion();
    }

    /**
     * Calcula y guarda el resumen semanal a partir de la semana indicada.
     *
     * <p>Receta: filtrar -&gt; calcular promedio -&gt; calcular dias de paz -&gt; guardar.
     * Cada paso lee y escribe el estado compartido de la instancia.</p>
     */
    public void calcularResumenSemanal(LocalDate semana) {
        Objects.requireNonNull(semana, "La semana es obligatoria");
        filtrarRegistrosDeLaSemana(semana);
        calcularPromedioEstres();
        calcularDiasDePaz();
        guardarResumen(semana);
    }

    private void filtrarRegistrosDeLaSemana(LocalDate semana) {
        LocalDate inicio = inicioDeSemana(semana);
        LocalDate fin = inicio.plusDays(DIAS_EN_SEMANA - 1L);
        registrosSemanaEnCalculo = new ArrayList<>();
        for (Emocion emocion : registros) {
            LocalDate fecha = emocion.getFecha();
            if (!fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
                registrosSemanaEnCalculo.add(emocion);
            }
        }
    }

    private void calcularPromedioEstres() {
        if (registrosSemanaEnCalculo.isEmpty()) {
            promedioEstresEnCalculo = 0d;
            return;
        }
        int suma = 0;
        for (Emocion emocion : registrosSemanaEnCalculo) {
            suma += emocion.getEscala().getValor();
        }
        promedioEstresEnCalculo = (double) suma / registrosSemanaEnCalculo.size();
    }

    private void calcularDiasDePaz() {
        int dias = 0;
        for (Emocion emocion : registrosSemanaEnCalculo) {
            if (!emocion.indicaEstres()) {
                dias++;
            }
        }
        diasDePazEnCalculo = dias;
    }

    private void guardarResumen(LocalDate semana) {
        LocalDate inicio = inicioDeSemana(semana);
        ResumenSemanal resumen = new ResumenSemanal(promedioEstresEnCalculo, diasDePazEnCalculo, inicio);
        resumenesSemanales.put(inicio, resumen);
    }

    /**
     * Devuelve el resumen semanal previamente calculado para la semana indicada, si existe.
     */
    public ResumenSemanal obtenerResumen(LocalDate semana) {
        return resumenesSemanales.get(inicioDeSemana(semana));
    }

    private static LocalDate inicioDeSemana(LocalDate fecha) {
        return fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}
