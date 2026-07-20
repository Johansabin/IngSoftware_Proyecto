package BC3_SoporteChat;

import java.time.LocalDateTime;

public class AuditData {

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AuditData() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public void registerUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
