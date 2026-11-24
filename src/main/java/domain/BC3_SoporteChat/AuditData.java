package BC3_SoporteChat;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class AuditData {

    private static final ZoneId ZONA = ZoneId.of("America/Lima");

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuditData() {
        LocalDateTime now = LocalDateTime.now(ZONA);
        this.createdAt = now;
        this.updatedAt = now;
    }

    public void registerUpdate() {
        this.updatedAt = LocalDateTime.now(ZONA);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
