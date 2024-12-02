package com.kzics.mplace.command.pallet;

import com.kzics.mplace.command.CommandBase;
import com.kzics.mplace.core.CanvasManager;
import com.kzics.mplace.menu.PalletMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PalletCommand extends CommandBase {
    private final CanvasManager canvasManager;
    public PalletCommand(CanvasManager canvasManager) {
        this.canvasManager = canvasManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }
        if(player.hasPermission("mplace.pallet")) {
            new PalletMenu(canvasManager).open(player);
        }
        return true;
    }
}
