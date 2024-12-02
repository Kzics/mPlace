package com.kzics.mplace.storage;

import com.kzics.mplace.config.CanvasConfiguration;
import com.kzics.mplace.config.CanvasState;
import com.kzics.mplace.core.Canvas;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CanvasStorage {

    private final File file;
    private final FileConfiguration config;

    public CanvasStorage(File dataFolder) {
        this.file = new File(dataFolder, "canvas.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveCanvas(Canvas canvas, Set<Material> blockWhitelist) {
        config.set("canvas.center.world", canvas.center().getWorld().getName());
        config.set("canvas.center.x", canvas.center().getX());
        config.set("canvas.center.y", canvas.center().getY());
        config.set("canvas.center.z", canvas.center().getZ());
        config.set("canvas.size", canvas.size());
        config.set("canvas.initialSize", canvas.initialSize());
        config.set("canvas.scaleFactor", canvas.scaleFactor());
        config.set("canvas.state", canvas.state().name());

        // Convertir la whitelist en une liste de noms
        List<String> whitelist = new ArrayList<>();
        for (Material material : blockWhitelist) {
            whitelist.add(material.name());
        }
        config.set("blockWhitelist", whitelist);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Canvas loadCanvas() {
        if (!config.contains("canvas.center.world")) {
            return null;
        }

        String worldName = config.getString("canvas.center.world");
        double x = config.getDouble("canvas.center.x");
        double y = config.getDouble("canvas.center.y");
        double z = config.getDouble("canvas.center.z");
        Location center = new Location(Bukkit.getWorld(worldName), x, y, z);

        int size = config.getInt("canvas.size");
        int initialSize = config.getInt("canvas.initialSize");
        double scaleFactor = config.getDouble("canvas.scaleFactor");
        String stateName = config.getString("canvas.state");
        CanvasState state = CanvasState.valueOf(stateName);

        CanvasConfiguration configuration = new CanvasConfiguration(center, initialSize, scaleFactor);
        Canvas canvas = new Canvas(configuration);
        canvas.state(state);
        canvas.updateSize(size / (double) initialSize);

        return canvas;
    }

    public Set<Material> loadBlockWhitelist() {
        Set<Material> blockWhitelist = new HashSet<>();
        if (config.contains("blockWhitelist")) {
            for (String materialName : config.getStringList("blockWhitelist")) {
                try {
                    blockWhitelist.add(Material.valueOf(materialName));
                } catch (IllegalArgumentException e) {
                    Bukkit.getLogger().warning("Invalid material in whitelist: " + materialName);
                }
            }
        }
        return blockWhitelist;
    }
}
