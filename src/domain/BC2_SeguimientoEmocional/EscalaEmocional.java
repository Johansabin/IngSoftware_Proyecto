package BC2_SeguimientoEmocional;

import java.util.Objects;

/**
 * Representa la intensidad de una emocion registrada por un estudiante.
 *
 * <p>Estilo de programacion aplicado: <b>Things</b>. La categoria se deriva
 * internamente a partir del valor y nunca se expone como un dato mutable
 * independiente; toda interaccion con el objeto ocurre a traves de sus
 * metodos publicos (el dato nunca se accede ni se decide "desde afuera").</p>
 */
public class EscalaEmocional {

    private static final int VALOR_MINIMO = 1;
    private static final int VALOR_MAXIMO = 10;
    private static final int LIMITE_BAJO = 3;
    private static final int LIMITE_MEDIO = 7;

    private static final String CATEGORIA_BAJO = "BAJO";
    private static final String CATEGORIA_MEDIO = "MEDIO";
    private static final String CATEGORIA_ALTO = "ALTO";

    private final int valor;
    private final String categoria;

    /**
     * Crea una escala emocional calculando automaticamente su categoria.
     *
     * @param valor intensidad emocional entre {@value #VALOR_MINIMO} y {@value #VALOR_MAXIMO}
     * @throws IllegalArgumentException si el valor esta fuera de rango
     */
    public EscalaEmocional(int valor) {
        validarRango(valor);
        this.valor = valor;
        this.categoria = categorizar(valor);
    }

    /**
     * Regla de validez del valor. Cambia solo si cambia el rango permitido
     * de la escala (independiente de como se categoriza ese valor).
     */
    private static void validarRango(int valor) {
        if (valor < VALOR_MINIMO || valor > VALOR_MAXIMO) {
            throw new IllegalArgumentException(
                "El valor de la escala debe estar entre " + VALOR_MINIMO + " y " + VALOR_MAXIMO
                    + ", pero se recibio " + valor);
        }
    }

    /**
     * Regla de categorizacion. Cambia solo si cambian los umbrales o el
     * numero de categorias, independientemente de la regla de validez.
     */
    private static String categorizar(int valor) {
        if (valor <= LIMITE_BAJO) {
            return CATEGORIA_BAJO;
        }
        if (valor <= LIMITE_MEDIO) {
            return CATEGORIA_MEDIO;
        }
        return CATEGORIA_ALTO;
    }

    public int getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    /**
     * Indica si esta escala corresponde a un nivel de estres alto.
     */
    public boolean esAlta() {
        return CATEGORIA_ALTO.equals(categoria);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EscalaEmocional)) {
            return false;
        }
        EscalaEmocional that = (EscalaEmocional) obj;
        return valor == that.valor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return "EscalaEmocional{valor=" + valor + ", categoria='" + categoria + "'}";
    }
}
