package com.kzics.mplace.core;

import com.kzics.mplace.Main;
import com.kzics.mplace.config.CanvasConfiguration;
import com.kzics.mplace.config.CanvasState;
import com.kzics.mplace.storage.CanvasStorage;
import com.kzics.mplace.visual.CanvasVisualizer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.io.File;

public class CanvasManager {
    private Canvas canvas;
    private final CanvasVisualizer visualizer = new CanvasVisualizer();
    private final BlockWhitelist blockWhitelist = new BlockWhitelist();
    private final CanvasStorage storage;

    public CanvasManager(Main main) {
        File dataFolder = main.getDataFolder();
        storage = new CanvasStorage(dataFolder);
        this.storage.loadCanvas();
    }

    public void initializeCanvas(CanvasConfiguration config) {
        if (canvas != null) {
            visualizer.clearCanvas(canvas.center(), canvas.size());
        }

        this.canvas = new Canvas(config);
        visualizer.renderCanvas(canvas.center(), canvas.size());
    }

    public void updateCanvasScale(double factor) {
        if (canvas != null && canvas.state() == CanvasState.ACTIVE) {
            int initialSize = canvas.initialSize();
            int newSize = (int) (initialSize * factor);
            int oldSize = canvas.size();

            canvas.updateSize(factor);
            visualizer.expandCanvas(canvas.center(), oldSize, newSize);
        }
    }

    public void saveData() {
        if (canvas != null) {
            storage.saveCanvas(canvas, blockWhitelist.allowedBlocks());
        }
    }

    public void loadData() {
        this.canvas = storage.loadCanvas();
        this.blockWhitelist.clearWhitelist();
        for (Material material : storage.loadBlockWhitelist()) {
            this.blockWhitelist.addBlock(material);
        }
    }


    public Canvas canvas() {
        return canvas;
    }

    public boolean isCanvasSet() {
        return canvas != null;
    }

    public void activateCanvas() {
        if (canvas != null) {
            canvas.state(CanvasState.ACTIVE);
        }
    }

    public void pauseCanvas() {
        if (canvas != null) {
            canvas.state(CanvasState.PAUSED);
        }
    }

    public void deactivateCanvas() {
        if (canvas != null) {
            canvas.state(CanvasState.INACTIVE);
        }
    }

    public BlockWhitelist blockWhitelist() {
        return blockWhitelist;
    }
}
