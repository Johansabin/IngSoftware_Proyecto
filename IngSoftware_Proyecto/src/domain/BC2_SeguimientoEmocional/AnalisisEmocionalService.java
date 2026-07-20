package BC2_SeguimientoEmocional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Servicio de dominio que orquesta el analisis emocional de una bitacora.
 *
 * <p>Estilo de programacion aplicado: <b>Pipeline</b>. Cada paso del analisis
 * es una funcion pura ({@code Function<Entrada, Salida>}) sin estado
 * compartido entre pasos; las funciones se encadenan con {@code andThen}
 * formando una tuberia de transformaciones, y solo el ultimo eslabon
 * produce un efecto observable (imprimir el resultado).</p>
 */
public class AnalisisEmocionalService {

    private static final int MINIMO_DIAS_PATRON = 3;
    private static final int DIAS_EN_SEMANA_MENOS_UNO = 6;

    /**
     * Genera el resumen semanal de una bitacora mediante una tuberia de
     * funciones puras y publica el resultado del analisis.
     */
    public void generarResumen(BitacoraEmocional bitacora, LocalDate semana) {
        Objects.requireNonNull(bitacora, "La bitacora es obligatoria");
        Objects.requireNonNull(semana, "La semana es obligatoria");

        Function<BitacoraEmocional, List<Emocion>> obtenerRegistrosDeLaSemana =
            b -> filtrarPorSemana(b.getRegistros(), semana);

        Function<List<Emocion>, ResumenSemanal> construirResumen =
            registros -> new ResumenSemanal(
                calcularPromedio(registros),
                contarDiasDePaz(registros),
                inicioDeSemana(semana));

        ResumenSemanal resumen = obtenerRegistrosDeLaSemana.andThen(construirResumen).apply(bitacora);
        imprimirResumen(resumen);
    }

    /**
     * Detecta patrones de estres sostenido mediante una tuberia de funciones
     * puras que filtran, agrupan y evaluan los registros de la bitacora.
     */
    public void detectarPatronesDeEstres(BitacoraEmocional bitacora) {
        Objects.requireNonNull(bitacora, "La bitacora es obligatoria");

        Function<BitacoraEmocional, List<Emocion>> obtenerRegistrosDeEstres =
            b -> soloRegistrosDeEstres(b.getRegistros());

        Function<List<Emocion>, Map<String, Long>> agruparPorTipo =
            AnalisisEmocionalService::contarPorTipo;

        Function<Map<String, Long>, List<String>> detectarTiposConPatron =
            conteos -> conteos.entrySet().stream()
                .filter(entrada -> entrada.getValue() >= MINIMO_DIAS_PATRON)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<String> patrones = obtenerRegistrosDeEstres
            .andThen(agruparPorTipo)
            .andThen(detectarTiposConPatron)
            .apply(bitacora);

        imprimirPatrones(patrones);
    }

    private static List<Emocion> filtrarPorSemana(List<Emocion> registros, LocalDate semana) {
        LocalDate inicio = inicioDeSemana(semana);
        LocalDate fin = inicio.plusDays(DIAS_EN_SEMANA_MENOS_UNO);
        return registros.stream()
            .filter(e -> !e.getFecha().isBefore(inicio) && !e.getFecha().isAfter(fin))
            .collect(Collectors.toList());
    }

    private static List<Emocion> soloRegistrosDeEstres(List<Emocion> registros) {
        return registros.stream()
            .filter(Emocion::indicaEstres)
            .collect(Collectors.toList());
    }

    private static Map<String, Long> contarPorTipo(List<Emocion> registros) {
        Map<String, Long> conteos = new HashMap<>();
        for (Emocion emocion : registros) {
            conteos.merge(emocion.getTipo(), 1L, Long::sum);
        }
        return conteos;
    }

    private static double calcularPromedio(List<Emocion> registros) {
        return registros.stream()
            .mapToInt(e -> e.getEscala().getValor())
            .average()
            .orElse(0d);
    }

    private static int contarDiasDePaz(List<Emocion> registros) {
        return (int) registros.stream()
            .filter(e -> !e.indicaEstres())
            .count();
    }

    private static LocalDate inicioDeSemana(LocalDate fecha) {
        return fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    private static void imprimirResumen(ResumenSemanal resumen) {
        System.out.println("Resumen semanal: " + resumen);
    }

    private static void imprimirPatrones(List<String> patrones) {
        if (patrones.isEmpty()) {
            System.out.println("No se detectaron patrones de estres sostenido.");
            return;
        }
        System.out.println("Patrones de estres detectados en: " + String.join(", ", patrones));
    }
}
