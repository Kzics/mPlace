package com.kzics.mplace.config;

import org.bukkit.Location;

public record CanvasConfiguration(
        Location centerLocation,
        int initialSize,
        double scaleFactor
) {}
