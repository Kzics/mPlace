package com.kzics.mplace.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Long> playerCooldowns = new HashMap<>();
    private long cooldownTime;

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

    public void changeCooldownTime(long newCooldownTime) {
        this.cooldownTime = newCooldownTime;
    }

    public long getTimeRemaining(UUID playerId) {
        Long lastPlacementTime = playerCooldowns.get(playerId);
        if (lastPlacementTime == null) return 0;

        long elapsedTime = System.currentTimeMillis() - lastPlacementTime;
        return Math.max(0, cooldownTime - elapsedTime);
    }

    public String getFormattedTimeRemaining(UUID playerId) {
        long timeRemaining = getTimeRemaining(playerId);

        if (timeRemaining <= 0) {
            return "00:00";
        }

        long minutes = timeRemaining / 60000;
        long seconds = (timeRemaining % 60000) / 1000;

        return String.format("%02d:%02d", minutes, seconds);
    }
}
