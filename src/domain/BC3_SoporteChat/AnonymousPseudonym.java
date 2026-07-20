package BC3_SoporteChat;

import java.util.Objects;
import java.util.UUID;

public class AnonymousPseudonym {

    private static final String ALIAS_PREFIX = "student-";
    private static final int UUID_FRAGMENT_LENGTH = 8;

    private final String value;

    public AnonymousPseudonym(String value) {
        this.value = validateValue(value);
    }

    private String validateValue(String value) {
        Objects.requireNonNull(value, "The pseudonym is required");

        String normalizedValue = value.trim();
        if (normalizedValue.isBlank()) {
            throw new IllegalArgumentException("The pseudonym cannot be blank");
        }

        return normalizedValue;
    }

    public static AnonymousPseudonym generate() {
        String fragment = UUID.randomUUID().toString().substring(0, UUID_FRAGMENT_LENGTH);
        return new AnonymousPseudonym(ALIAS_PREFIX + fragment);
    }

    public String getValue() {
        return value;
    }
}
