package com.kzics.mplace.command.canvas;

import com.kzics.mplace.Main;
import com.kzics.mplace.command.CommandBase;
import com.kzics.mplace.command.ICommand;
import com.kzics.mplace.command.canvas.sub.CanvasClearCommand;
import com.kzics.mplace.command.canvas.sub.CanvasCooldownCommand;
import com.kzics.mplace.command.canvas.sub.CanvasExpandCommand;
import com.kzics.mplace.command.canvas.sub.CanvasStateCommand;
import com.kzics.mplace.core.CanvasManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CanvasCommand extends CommandBase implements TabCompleter {

    public CanvasCommand(Main main) {
        registerSubCommand("state", new CanvasStateCommand(main.canvasManager()));
        registerSubCommand("expand", new CanvasExpandCommand(main.canvasManager()));
        registerSubCommand("cooldown", new CanvasCooldownCommand(main.cooldownManager()));
        registerSubCommand("clear", new CanvasClearCommand(main.canvasManager()));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            for (String subCommand : subCommands.keySet()) {
                if (subCommand.startsWith(prefix)) {
                    completions.add(subCommand);
                }
            }
        } else if (args.length > 1) {
            String subCommandName = args[0].toLowerCase();
            if (subCommands.containsKey(subCommandName)) {
                ICommand subCommand = subCommands.get(subCommandName);

                if (subCommand instanceof TabCompleter completer) {
                    return completer.onTabComplete(sender, command, alias, shiftArgs(args));
                }
            }
        }

        return completions;
    }

    private String[] shiftArgs(String[] args) {
        String[] shiftedArgs = new String[args.length - 1];
        System.arraycopy(args, 1, shiftedArgs, 0, args.length - 1);
        return shiftedArgs;
    }
}
