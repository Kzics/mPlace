package com.kzics.mplace.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CommandBase implements CommandExecutor {
    protected final HashMap<String, ICommand> subCommands = new HashMap<>();

    public CommandBase() {

    }

    public void registerSubCommand(String name, ICommand command) {
        subCommands.put(name.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;
        if (args.length > 0) {
            ICommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand != null) {
                if(!sender.hasPermission(subCommand.getPermission())){
                    //sender.sendMessage(ColorsUtil.translate.apply("&cYou don't have permission to execute this command."));
                    return true;
                }
                subCommand.execute(sender, args);
                return true;
            }
            //subCommands.get("help").execute(sender, args);
        }

        //final GamePlayer gamePlayer = coreApi.getServicesManager().getService(PlayerService.class).getPlayerData(player.getUniqueId());
        //new RunesMenu(coreApi.getServicesManager(),null,null).open(gamePlayer);

        return false;
    }

}
