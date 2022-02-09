package codurance;

import java.util.UUID;

public record UserId(UUID uuid) {
    public UserId() {
        this(UUID.randomUUID());
    }
}
