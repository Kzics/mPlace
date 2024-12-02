package com.kzics.mplace.menu;

import com.kzics.mplace.core.CanvasManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PalletMenu extends CanvasMenu {
    private int currentPage = 0;
    private final List<Material> blockWhitelist;

    public PalletMenu(CanvasManager canvasManager) {
        super(Component.text("Pallet"), 54);
        this.blockWhitelist = new ArrayList<>(canvasManager.blockWhitelist().allowedBlocks());
    }

    @Override
    public void open(Player player) {
        updatePage();
        player.openInventory(inventory);
    }

    private void updatePage() {
        inventory.clear();

        int startIndex = currentPage * 45;
        int endIndex = Math.min(startIndex + 45, blockWhitelist.size());

        for (int i = startIndex; i < endIndex; i++) {
            inventory.addItem(new ItemStack(blockWhitelist.get(i)));
        }

        addNavigationControls();
    }

    private void addNavigationControls() {
        inventory.setItem(49, new ItemStack(Material.BARRIER, 1));

        if (currentPage > 0) {
            ItemStack previousArrow = new ItemStack(Material.ARROW);
            previousArrow.editMeta(meta -> meta.displayName(Component.text("Previous Page", NamedTextColor.YELLOW)));
            inventory.setItem(48, previousArrow);
        }

        if ((currentPage + 1) * 45 < blockWhitelist.size()) {
            ItemStack nextArrow = new ItemStack(Material.ARROW);
            nextArrow.editMeta(meta -> meta.displayName(Component.text("Next Page", NamedTextColor.YELLOW)));
            inventory.setItem(50, nextArrow);
        }
    }

    @Override
    public void handle(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) return;

        if (item.getType() == Material.BARRIER) {
            player.closeInventory();
            return;
        }

        if (item.getType() == Material.ARROW) {
            Component name = item.getItemMeta() != null ? item.getItemMeta().displayName() : null;
            if (name != null && name.equals(Component.text("Previous Page", NamedTextColor.YELLOW))) {
                currentPage = Math.max(currentPage - 1, 0);
                updatePage();
            } else if (name != null && name.equals(Component.text("Next Page", NamedTextColor.YELLOW))) {
                currentPage++;
                updatePage();
            }
            return;
        }

        // Si c'est un bloc, sélectionnez-le pour le joueur
        if (blockWhitelist.contains(item.getType())) {
            player.getInventory().setItemInMainHand(item);
            player.closeInventory();
            player.sendMessage(Component.text("You have selected " + item.getType().name(), NamedTextColor.GREEN));
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
