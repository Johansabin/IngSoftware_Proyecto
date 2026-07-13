package BC3_SoporteChat;

import java.util.Objects;

public class SeudonimoAnonimo {
    private String alias;

    public SeudonimoAnonimo() {
    }

    public SeudonimoAnonimo(String alias) {
        this.alias = validarAlias(alias);
    }

    private String validarAlias(String alias) {
        Objects.requireNonNull(alias, "El alias es obligatorio");

        String aliasNormalizado = alias.trim();
        if (aliasNormalizado.isBlank()) {
            throw new IllegalArgumentException("El alias no puede estar vacio");
        }

        return aliasNormalizado;
    }

    public String getAlias() {
        return alias;
    }

}
