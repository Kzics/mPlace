package com.kzics.mplace;

import com.kzics.mplace.command.canvas.CanvasCommand;
import com.kzics.mplace.command.pallet.PalletCommand;
import com.kzics.mplace.command.whitelist.BlockWhitelistCommand;
import com.kzics.mplace.config.CanvasConfiguration;
import com.kzics.mplace.core.CanvasManager;
import com.kzics.mplace.core.CooldownManager;
import com.kzics.mplace.listener.CanvasListeners;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private CanvasManager canvasManager;
    private CooldownManager cooldownManager;


    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new CanvasListeners(this), this);
    }
    private void registerCommands() {
        getCommand("canvas").setExecutor(new CanvasCommand(canvasManager));
        getCommand("pallet").setExecutor(new PalletCommand(canvasManager));
        getCommand("blockwhitelist").setExecutor(new BlockWhitelistCommand(canvasManager));
        getCommand("blockwhitelist").setTabCompleter(new BlockWhitelistCommand(canvasManager));
    }

    @Override
    public void onEnable() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        canvasManager = new CanvasManager(this);
        cooldownManager = new CooldownManager(5000);
        registerListeners();
        registerCommands();
        canvasManager.loadData();
        //canvasManager.blockWhitelist().addBlock(Material.DIRT);
    }

    @Override
    public void onDisable() {
        canvasManager.saveData();
    }

    public CanvasManager canvasManager() {
        return canvasManager;
    }

    public CooldownManager cooldownManager() {
        return cooldownManager;
    }
}
