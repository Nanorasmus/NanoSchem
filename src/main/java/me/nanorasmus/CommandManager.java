package me.nanorasmus;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CommandManager implements CommandExecutor {
    Main main;

    public CommandManager(Main main) {
        this.main = main;
        main.getCommand("NanoSchem").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("NanoSchem") && sender.isOp() && sender instanceof Player) {
            switch (args[0] == null ? null : args[0].toLowerCase()) {
                case "load":
                    if (args.length < 2 || args[1] == null) {
                        sender.sendMessage("Please enter the name of a schematic!");
                        return true;
                    }
                    sender.sendMessage("Getting " + args[1]);
                    Structure structure = main.saveManager.Load(args[1]);
                    if (structure == null) {
                        sender.sendMessage(args[1] + " is either missing or corrupt!");
                        return true;
                    }
                    sender.sendMessage("Pasting: " + args[1]);
                    structure.Build(((Player)sender).getLocation());
                    sender.sendMessage("Sucessfully pasted" + args[1]);
                case "save":
                    if (args.length < 2 || args[1] == null) {
                        sender.sendMessage("Please enter the name of your schematic!");
                        return true;
                    }
                    if (!main.selectionManager.selections.containsKey(((Player) sender).getUniqueId())) {
                        sender.sendMessage("You do not have a selection!");
                        return true;
                    }
                    Pair<Location, Location> selection = main.selectionManager.selections.get(((Player) sender).getUniqueId());
                    if (selection.left() == null || selection.right() == null) {
                        sender.sendMessage("You do not have a valid selection!");
                        return true;
                    }
                    sender.sendMessage("Saving schematic with name: " + args[1]);
                    try {
                        main.saveManager.Save(new Structure(selection.left(), selection.right(), ((Player) sender).getLocation()), args[1]);
                    } catch (IOException e) {
                        sender.sendMessage("Failed to save, check console for more info!");
                        e.printStackTrace();
                    }
                    sender.sendMessage("Saved schematic to: " + args[1]);
                    break;
                default:
                    sender.sendMessage("That is not a valid command!");
                    break;
            }
        }
        return true;
    }
}
