package com.kzics.mplace.command.whitelist.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.core.CanvasManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class BlockWhitelistAddCommand implements ICommand, TabCompleter {

    private final CanvasManager canvasManager;

    public BlockWhitelistAddCommand(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Add a block to the whitelist";
    }

    @Override
    public String getPermission() {
        return "canvas.blockwhitelist.add";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid arguments. Usage: /blockwhitelist add <block>");
            return;
        }

        String block = args[1];
        Material material;
        try {
            material = Material.valueOf(block.toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("Invalid block: " + block);
            return;
        }
        canvasManager.blockWhitelist().addBlock(material);
        sender.sendMessage("Block " + material.name() + " added to the whitelist.");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 2) {
            String input = args[1].toUpperCase();
            for (Material material : Material.values()) {
                if (material.name().contains(input)) {
                    completions.add(material.name());
                }
            }
        }
        return completions;
    }
}
