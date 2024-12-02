package com.kzics.mplace.command.whitelist;

import com.kzics.mplace.command.CommandBase;
import com.kzics.mplace.command.whitelist.sub.BlockWhitelistAddCommand;
import com.kzics.mplace.command.whitelist.sub.BlockWhitelistRemoveCommand;
import com.kzics.mplace.core.CanvasManager;

public class BlockWhitelistCommand extends CommandBase {

    public BlockWhitelistCommand(CanvasManager canvasManager) {
        registerSubCommand("add", new BlockWhitelistAddCommand(canvasManager));
        registerSubCommand("remove", new BlockWhitelistRemoveCommand(canvasManager));
    }
}
