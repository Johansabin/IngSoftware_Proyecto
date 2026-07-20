package BC3_SoporteChat;

import java.util.UUID;

public class SeudonimoAnonimo {

    private static final String PREFIJO_ALIAS = "estudiante-";
    private static final int LONGITUD_FRAGMENTO_UUID = 8;

    private final String alias;

    public SeudonimoAnonimo(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new IllegalArgumentException("El alias anonimo es obligatorio");
        }

        this.alias = alias.trim();
    }

    public static SeudonimoAnonimo generar() {
        String fragmento = UUID.randomUUID().toString().substring(0, LONGITUD_FRAGMENTO_UUID);
        return new SeudonimoAnonimo(PREFIJO_ALIAS + fragmento);
    }

    public String getAlias() {
        return alias;
    }

}
