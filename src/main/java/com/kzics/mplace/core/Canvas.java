package com.kzics.mplace.core;

import com.kzics.mplace.config.CanvasConfiguration;
import com.kzics.mplace.config.CanvasState;
import org.bukkit.Location;

public class Canvas {
    private Location center;
    private int size;
    private final int initialSize; // Nouvelle variable pour stocker la taille initiale
    private double scaleFactor;
    private CanvasState state;

    public Canvas(CanvasConfiguration config) {
        this.center = config.centerLocation();
        this.size = config.initialSize();
        this.initialSize = config.initialSize(); // Initialiser avec la taille initiale
        this.scaleFactor = config.scaleFactor();
        this.state = CanvasState.ACTIVE;
    }

    public Location center() {
        return center;
    }

    public void center(Location center) {
        this.center = center;
    }

    public int size() {
        return size;
    }

    public int initialSize() {
        return initialSize;
    }

    public void updateSize(double factor) {
        this.size = (int) (this.initialSize * factor); // Basé sur la taille initiale
    }

    public double scaleFactor() {
        return scaleFactor;
    }

    public void scaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    public CanvasState state() {
        return state;
    }

    public void state(CanvasState state) {
        this.state = state;
    }

    public boolean isWithinBounds(Location location) {
        int halfSize = size / 2;
        return location.getBlockX() >= center.getBlockX() - halfSize &&
                location.getBlockX() <= center.getBlockX() + halfSize &&
                location.getBlockZ() >= center.getBlockZ() - halfSize &&
                location.getBlockZ() <= center.getBlockZ() + halfSize;
    }
}
