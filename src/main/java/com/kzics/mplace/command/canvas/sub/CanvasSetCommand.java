package com.kzics.mplace.command.canvas.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.config.CanvasConfiguration;
import com.kzics.mplace.core.CanvasManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CanvasSetCommand implements ICommand {

    private final CanvasManager canvasManager;
    public CanvasSetCommand(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;

    }
    @Override
    public String getName() {
        return "set";
    }

    @Override
    public String getDescription() {
        return "Set the canvas";
    }

    @Override
    public String getPermission() {
        return "mplace.canvas.set";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command");
            return;
        }
        CanvasConfiguration canvasConfiguration = new CanvasConfiguration(player.getLocation(), 10, 5);
        canvasManager.initializeCanvas(canvasConfiguration);
    }
}
