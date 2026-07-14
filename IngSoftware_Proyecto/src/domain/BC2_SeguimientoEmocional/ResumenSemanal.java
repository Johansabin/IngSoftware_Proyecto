package BC2_Seguimiento-Emocional;

import java.time.LocalDate;

/**
 * Value Object de solo lectura: resultado del análisis semanal de un estudiante.
 * No tiene setters porque representa un resultado calculado en un momento dado,
 * no una entidad que deba mutar.
 */
public final class ResumenSemanal {

    private final double promedioEstres;
    private final int diasDePaz;
    private final LocalDate semanaInicio;

    public ResumenSemanal(double promedioEstres, int diasDePaz, LocalDate semanaInicio) {
        if (semanaInicio == null) {
            throw new IllegalArgumentException("semanaInicio es obligatorio");
        }
        this.promedioEstres = promedioEstres;
        this.diasDePaz = diasDePaz;
        this.semanaInicio = semanaInicio;
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
    public String toString() {
        return "ResumenSemanal{semanaInicio=" + semanaInicio
                + ", promedioEstres=" + promedioEstres
                + ", diasDePaz=" + diasDePaz + "}";
    }
}