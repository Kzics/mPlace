package com.kzics.mplace.visual;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockTypes;
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
        int endX = center.getBlockX() + halfSize;
        int endZ = center.getBlockZ() + halfSize;

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                Location location = new Location(world, x, center.getBlockY(), z);

                if (location.getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.WHITE_WOOL);
                }
            }
        }
    }

    public void clearCanvas(Location center, int size) {
        World world = center.getWorld();
        if (world == null) return;

        int halfSize = size / 2;
        int startX = center.getBlockX() - halfSize;
        int startZ = center.getBlockZ() - halfSize;
        int endX = center.getBlockX() + halfSize;
        int endZ = center.getBlockZ() + halfSize;

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                Location location = new Location(world, x, center.getBlockY(), z);
                location.getBlock().setType(Material.AIR);
            }
        }
    }

    public void expandCanvas(Location center, int oldSize, int newSize) {
        if (newSize <= oldSize) return; // Pas d'agrandissement si la taille est plus petite ou égale.

        World world = center.getWorld();
        if (world == null) return;

        int oldHalfSize = oldSize / 2;
        int newHalfSize = newSize / 2;

        int oldStartX = center.getBlockX() - oldHalfSize;
        int oldStartZ = center.getBlockZ() - oldHalfSize;
        int oldEndX = center.getBlockX() + oldHalfSize;
        int oldEndZ = center.getBlockZ() + oldHalfSize;

        int newStartX = center.getBlockX() - newHalfSize;
        int newStartZ = center.getBlockZ() - newHalfSize;
        int newEndX = center.getBlockX() + newHalfSize;
        int newEndZ = center.getBlockZ() + newHalfSize;

        // Parcours des nouvelles zones pour remplir les blocs manquants
        for (int x = newStartX; x <= newEndX; x++) {
            for (int z = newStartZ; z <= newEndZ; z++) {
                if (x >= oldStartX && x <= oldEndX && z >= oldStartZ && z <= oldEndZ) {
                    continue; // Ignore les blocs déjà existants dans l'ancien canvas.
                }

                Location location = new Location(world, x, center.getBlockY(), z);
                if (location.getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(Material.WHITE_WOOL);
                }
            }
        }
    }
}
