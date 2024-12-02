package com.kzics.mplace.command.canvas.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.core.CanvasManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class CanvasClearCommand implements ICommand {
    private final CanvasManager canvasManager;
    public CanvasClearCommand(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clear the canvas";
    }

    @Override
    public String getPermission() {
        return "mplace.canvas.clear";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        canvasManager.clearCanvas();

        sender.sendMessage(Component.text("Canvas cleared", NamedTextColor.GREEN));
    }
}
