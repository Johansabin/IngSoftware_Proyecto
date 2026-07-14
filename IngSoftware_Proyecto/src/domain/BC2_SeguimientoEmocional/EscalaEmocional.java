package BC2_Seguimiento-Emocional;

/**
 * Value Object que representa la intensidad de una emoción en una escala de 1 a 10,
 * junto con la categoría derivada de ese valor (BAJO, MEDIO, ALTO).
 *
 * Es inmutable: una vez creado, su estado no cambia (evita bugs de mutación compartida).
 */
public final class EscalaEmocional {

    private static final int VALOR_MINIMO = 1;
    private static final int VALOR_MAXIMO = 10;
    private static final int LIMITE_CATEGORIA_BAJO = 3;
    private static final int LIMITE_CATEGORIA_MEDIO = 7;

    private final int valor;
    private final String categoria;

    private EscalaEmocional(int valor, String categoria) {
        this.valor = valor;
        this.categoria = categoria;
    }

    /**
     * Crea una escala emocional a partir de un valor numérico, derivando automáticamente
     * su categoría. Lanza IllegalArgumentException si el valor está fuera de rango
     * (validación defensiva, evita estados inválidos del dominio).
     *
     * @param valor intensidad reportada, entre 1 y 10
     * @return instancia inmutable de EscalaEmocional
     */
    public static EscalaEmocional desde(int valor) {
        if (valor < VALOR_MINIMO || valor > VALOR_MAXIMO) {
            throw new IllegalArgumentException(
                    "El valor de la escala emocional debe estar entre " + VALOR_MINIMO + " y " + VALOR_MAXIMO);
        }
        return new EscalaEmocional(valor, categorizar(valor));
    }

    private static String categorizar(int valor) {
        if (valor <= LIMITE_CATEGORIA_BAJO) {
            return "BAJO";
        }
        if (valor <= LIMITE_CATEGORIA_MEDIO) {
            return "MEDIO";
        }
        return "ALTO";
    }

    public int getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EscalaEmocional)) {
            return false;
        }
        EscalaEmocional otra = (EscalaEmocional) o;
        return valor == otra.valor && categoria.equals(otra.categoria);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(valor, categoria);
    }

    @Override
    public String toString() {
        return "EscalaEmocional{valor=" + valor + ", categoria='" + categoria + "'}";
    }
}