package com.kzics.mplace.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Long> playerCooldowns = new HashMap<>();
    private final long cooldownTime;

    public CooldownManager(long cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public boolean canPlaceBlock(UUID playerId) {
        long currentTime = System.currentTimeMillis();
        Long lastPlacementTime = playerCooldowns.get(playerId);

        if (lastPlacementTime == null || currentTime - lastPlacementTime >= cooldownTime) {
            playerCooldowns.put(playerId, currentTime);
            return true;
        }

        return false;
    }

    public long getTimeRemaining(UUID playerId) {
        Long lastPlacementTime = playerCooldowns.get(playerId);
        if (lastPlacementTime == null) return 0;

        long elapsedTime = System.currentTimeMillis() - lastPlacementTime;
        return Math.max(0, cooldownTime - elapsedTime);
    }
}
