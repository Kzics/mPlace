package com.kzics.mplace.command.canvas.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.config.CanvasState;
import com.kzics.mplace.core.CanvasManager;
import org.bukkit.command.CommandSender;

public class CanvasStateCommand implements ICommand {

    private final CanvasManager canvasManager;
    public CanvasStateCommand(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;

    }
    @Override
    public String getName() {
        return "state";
    }

    @Override
    public String getDescription() {
        return "Change the state of the canvas";
    }

    @Override
    public String getPermission() {
        return "mplace.canvas.state";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Invalid arguments");
            return;
        }

        String state = args[0];
        CanvasState canvasState;
        try {
            canvasState = CanvasState.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("Invalid state");
            return;
        }
        switch (canvasState) {
            case CanvasState.ACTIVE:
                canvasManager.activateCanvas();
                break;
            case CanvasState.INACTIVE:
                canvasManager.deactivateCanvas();
                break;
            case CanvasState.PAUSED:
                canvasManager.pauseCanvas();
                break;
            default:
                sender.sendMessage("Invalid state");
        }
    }
}
