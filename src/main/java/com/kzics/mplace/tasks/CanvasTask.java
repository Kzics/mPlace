package com.kzics.mplace.tasks;

import com.kzics.mplace.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CanvasTask extends BukkitRunnable {
    private final Main main;

    public CanvasTask(Main main) {
        this.main = main;
    }

    @Override
    public void run() {
        for (Player player : main.getServer().getOnlinePlayers()) {
            if(main.cooldownManager().getTimeRemaining(player.getUniqueId()) == 0) {
                player.sendActionBar(Component.text("✔ You can place a block", NamedTextColor.GREEN));
                continue;
            }
            Component remainingTimeComponent = Component.empty()
                    .append(Component.text("Next Placement: ", NamedTextColor.YELLOW))
                    .append(Component.text(main.cooldownManager().getFormattedTimeRemaining(player.getUniqueId()), NamedTextColor.GREEN));
            player.sendActionBar(remainingTimeComponent);
        }
    }
}
