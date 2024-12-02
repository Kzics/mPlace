package com.kzics.mplace.command.canvas.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.core.Canvas;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class CanvasDefaultBlockCommand implements ICommand {

    private final Canvas canvas;
    public CanvasDefaultBlockCommand(Canvas canvas) {
        this.canvas = canvas;

    }
    @Override
    public String getName() {
        return "default";
    }

    @Override
    public String getDescription() {
        return "Change the default block";
    }

    @Override
    public String getPermission() {
        return "mplace.canvas.default";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Invalid arguments");
            return;
        }

        String block = args[0];
        Material material;
        try {
            material = Material.valueOf(block.toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage("Invalid block");
            return;
        }
        canvas.defaultMaterial(material);
        sender.sendMessage(Component.text("Default block set to " + block, NamedTextColor.GREEN));
    }
}
