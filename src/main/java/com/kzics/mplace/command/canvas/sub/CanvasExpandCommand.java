package com.kzics.mplace.command.canvas.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.core.CanvasManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class CanvasExpandCommand implements ICommand {

    private final CanvasManager canvasManager;
    public CanvasExpandCommand(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;

    }
    @Override
    public String getName() {
        return "expand";
    }

    @Override
    public String getDescription() {
        return "Expand the canvas";
    }

    @Override
    public String getPermission() {
        return "mplace.canvas.expand";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid arguments");
            return;
        }

        int size;
        try {
            size = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid size");
            return;
        }
        canvasManager.updateCanvasScale(size);
        sender.sendMessage(Component.text("Canvas expanded to " + size, NamedTextColor.GREEN));
    }
}
