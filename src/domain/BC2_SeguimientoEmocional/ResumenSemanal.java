package BC2_SeguimientoEmocional;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Resultado agregado del analisis emocional de una semana especifica.
 *
 * <p>Estilo de programacion aplicado: <b>Things</b>. Es un valor inmutable
 * que se valida en construccion; no expone setters ni permite estados
 * invalidos una vez creado.</p>
 */
public class ResumenSemanal {

    private final double promedioEstres;
    private final int diasDePaz;
    private final LocalDate semanaInicio;

    public ResumenSemanal(double promedioEstres, int diasDePaz, LocalDate semanaInicio) {
        if (promedioEstres < 0) {
            throw new IllegalArgumentException(
                "El promedio de estres no puede ser negativo, se recibio " + promedioEstres);
        }
        if (diasDePaz < 0) {
            throw new IllegalArgumentException(
                "Los dias de paz no pueden ser negativos, se recibio " + diasDePaz);
        }
        this.promedioEstres = promedioEstres;
        this.diasDePaz = diasDePaz;
        this.semanaInicio = Objects.requireNonNull(semanaInicio, "La semana de inicio es obligatoria");
    }

    public double getPromedioEstres() {
        return promedioEstres;
    }

    public int getDiasDePaz() {
        return diasDePaz;
    }

    public LocalDate getSemanaInicio() {
        return semanaInicio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResumenSemanal)) {
            return false;
        }
        ResumenSemanal that = (ResumenSemanal) obj;
        return semanaInicio.equals(that.semanaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semanaInicio);
    }

    @Override
    public String toString() {
        return "ResumenSemanal{semanaInicio=" + semanaInicio
            + ", promedioEstres=" + promedioEstres
            + ", diasDePaz=" + diasDePaz + "}";
    }
}
