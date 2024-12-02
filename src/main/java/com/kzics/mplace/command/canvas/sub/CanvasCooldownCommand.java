package com.kzics.mplace.command.canvas.sub;

import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.core.CanvasManager;
import com.kzics.mplace.core.CooldownManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

public class CanvasCooldownCommand implements ICommand {
    private final CooldownManager cooldownManager;
    public CanvasCooldownCommand(CooldownManager cooldownManager) {
        this.cooldownManager = cooldownManager;
    }


    @Override
    public String getName() {
        return "cooldown";
    }

    @Override
    public String getDescription() {
        return "Set the cooldown for the canvas";
    }

    @Override
    public String getPermission() {
        return "mplace.canvas.cooldown";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Invalid arguments");
            return;
        }

        int cooldown;
        try {
            cooldown = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("Invalid cooldown", NamedTextColor.RED));
            return;
        }
        cooldownManager.changeCooldownTime((cooldown * 1000L));
        sender.sendMessage(Component.text("Cooldown set to " + cooldown, NamedTextColor.GREEN));
    }
}
