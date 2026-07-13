package BC3_SoporteChat;

import java.util.Objects;

public class AnonymousPseudonym {
    private String value;

    public AnonymousPseudonym() {
    }

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

    public String getValue() {
        return value;
    }
}

