package BC3_SoporteChat;

import java.util.UUID;

public class AnonymousPseudonym {

    private static final String ALIAS_PREFIX = "estudiante-";
    private static final int UUID_FRAGMENT_LENGTH = 8;

    private final String alias;

    public AnonymousPseudonym(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new IllegalArgumentException("El alias anonimo es obligatorio");
        }

        this.alias = alias.trim();
    }

    public static AnonymousPseudonym generate() {
        String fragment = UUID.randomUUID().toString().substring(0, UUID_FRAGMENT_LENGTH);
        return new AnonymousPseudonym(ALIAS_PREFIX + fragment);
    }

    public String getAlias() {
        return alias;
    }
}
