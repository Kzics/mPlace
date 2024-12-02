package com.kzics.mplace.command;

import com.kzics.mplace.command.ICommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandBase implements CommandExecutor, TabCompleter {
    protected final HashMap<String, ICommand> subCommands = new HashMap<>();

    public CommandBase() {}

    public void registerSubCommand(String name, ICommand command) {
        subCommands.put(name.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            ICommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand != null) {
                if (!sender.hasPermission(subCommand.getPermission())) {
                    sender.sendMessage("You don't have permission to execute this command.");
                    return true;
                }
                subCommand.execute(sender, args);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Proposer les sous-commandes
            String input = args[0].toLowerCase();
            for (String subCommand : subCommands.keySet()) {
                if (subCommand.startsWith(input)) {
                    completions.add(subCommand);
                }
            }
        } else if (args.length > 1) {
            // Déléguer le TabCompleter à la sous-commande
            ICommand subCommand = subCommands.get(args[0].toLowerCase());
            if (subCommand instanceof TabCompleter tabCompleter) {
                return tabCompleter.onTabComplete(sender, command, alias, args);
            }
        }

        return completions;
    }
}
