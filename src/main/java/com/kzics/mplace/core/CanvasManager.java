package com.kzics.mplace.core;

import com.kzics.mplace.config.CanvasConfiguration;
import com.kzics.mplace.config.CanvasState;
import com.kzics.mplace.visual.CanvasVisualizer;

public class CanvasManager {
    private Canvas canvas;
    private final CanvasVisualizer visualizer = new CanvasVisualizer();
    private final BlockWhitelist blockWhitelist = new BlockWhitelist();

    public void initializeCanvas(CanvasConfiguration config) {
        if (canvas != null) {
            visualizer.clearCanvas(canvas.center(), canvas.size());
        }

        this.canvas = new Canvas(config);
        visualizer.renderCanvas(canvas.center(), canvas.size());
    }

    public void updateCanvasScale(double factor) {
        if (canvas != null && canvas.state() == CanvasState.ACTIVE) {
            visualizer.clearCanvas(canvas.center(), canvas.size());
            canvas.updateSize(factor);
            visualizer.renderCanvas(canvas.center(), canvas.size());
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