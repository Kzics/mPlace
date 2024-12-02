package com.kzics.mplace.config;

import org.bukkit.Location;
import org.bukkit.Material;

public record CanvasConfiguration(
        Location centerLocation,
        Material defaultMaterial,
        int initialSize,
        double scaleFactor
) {}
