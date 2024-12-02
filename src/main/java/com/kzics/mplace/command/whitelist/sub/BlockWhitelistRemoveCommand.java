package com.kzics.mplace.command.whitelist.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.core.CanvasManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class BlockWhitelistRemoveCommand implements ICommand {

    private final CanvasManager canvasManager;
    public BlockWhitelistRemoveCommand(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Remove a block from the whitelist";
    }

    @Override
    public String getPermission() {
        return "canvas.blockwhitelist.remove";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid arguments");
            return;
        }

        String block = args[1];
        Material material;
        try {
            material = Material.valueOf(block.toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("Invalid block");
            return;
        }
        canvasManager.blockWhitelist().removeBlock(material);
        sender.sendMessage(Component.text("Block " + material.name() + " removed from the whitelist", NamedTextColor.GREEN));
    }
}
