package com.kzics.mplace.listener;

import com.kzics.mplace.Main;
import com.kzics.mplace.config.CanvasConfiguration;
import com.kzics.mplace.config.CanvasState;
import com.kzics.mplace.core.CanvasManager;
import com.kzics.mplace.core.CooldownManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class CanvasListeners implements Listener {

    private final CanvasManager canvasManager;
    private final CooldownManager cooldownManager;
    public CanvasListeners(Main main) {
        this.canvasManager = main.canvasManager();
        this.cooldownManager = main.cooldownManager();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        if(canvasManager.canvas().state() == CanvasState.INACTIVE || canvasManager.canvas().state() == CanvasState.PAUSED) {
            player.sendMessage(Component.text("Canvas is inactive/Paused", NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }

        if (!canvasManager.isCanvasSet()) {
            player.sendMessage(Component.text("Canvas is not set!"));
            event.setCancelled(true);
            return;
        }

        if (!cooldownManager.canPlaceBlock(playerId)) {
            long remaining = cooldownManager.getTimeRemaining(playerId) / 1000;
            player.sendMessage(Component.text("You must wait " + remaining + " seconds before placing another block", NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }

        Location location = event.getBlock().getLocation();
        Material blockType = event.getBlock().getType();

        if (!canvasManager.canvas().isWithinBounds(location)) {
            player.sendMessage(Component.text("You can only place blocks within the canvas", NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }
        if (!canvasManager.blockWhitelist().isAllowed(blockType)) {
            player.sendMessage(Component.text("You can only place whitelisted blocks", NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }

        player.sendMessage(Component.text("Block placed", NamedTextColor.GREEN));
        location.getBlock().getRelative(BlockFace.DOWN).setType(blockType);
        event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        /*CanvasConfiguration canvasConfig = new CanvasConfiguration(player.getLocation(), 10, 5);

        if(!canvasManager.isCanvasSet()) {
            canvasManager.initializeCanvas(canvasConfig);
        }*/
    }
}
