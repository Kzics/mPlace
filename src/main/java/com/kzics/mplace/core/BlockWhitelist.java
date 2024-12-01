package com.kzics.mplace.core;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public class BlockWhitelist {
    private final Set<Material> allowedBlocks = new HashSet<>();

    public void addBlock(Material material) {
        allowedBlocks.add(material);
    }

    public void removeBlock(Material material) {
        allowedBlocks.remove(material);
    }

    public boolean isAllowed(Material material) {
        return allowedBlocks.contains(material);
    }

    public Set<Material> getAllowedBlocks() {
        return Set.copyOf(allowedBlocks);
    }

    public void clearWhitelist() {
        allowedBlocks.clear();
    }
}
