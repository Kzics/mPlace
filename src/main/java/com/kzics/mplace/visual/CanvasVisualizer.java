package com.kzics.mplace.visual;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class CanvasVisualizer {

    public void renderCanvas(Location center, int size) {
        World world = center.getWorld();
        if (world == null) return;

        int halfSize = size / 2;
        int startX = center.getBlockX() - halfSize;
        int startZ = center.getBlockZ() - halfSize;

        for (int x = startX; x <= startX + size; x++) {
            for (int z = startZ; z <= startZ + size; z++) {
                Location blockLocation = new Location(world, x, center.getBlockY() - 1, z);
                blockLocation.getBlock().setType(Material.WHITE_WOOL);
            }
        }
    }

    public void clearCanvas(Location center, int size) {
        World world = center.getWorld();
        if (world == null) return;

        int halfSize = size / 2;
        int startX = center.getBlockX() - halfSize;
        int startZ = center.getBlockZ() - halfSize;

        for (int x = startX; x <= startX + size; x++) {
            for (int z = startZ; z <= startZ + size; z++) {
                Location blockLocation = new Location(world, x, center.getBlockY() - 1, z);
                blockLocation.getBlock().setType(Material.AIR);
            }
        }
    }
}
