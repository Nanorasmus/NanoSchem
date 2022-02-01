package me.nanorasmus;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {
    Main main;

    public TabCompletion(Main main) {
        this.main = main;
        this.main.getCommand("NanoSchem").setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("NanoSchem") && args.length >= 0){
            if(sender instanceof Player && sender.isOp()){
                List<String> list = new ArrayList<>();

                if (args.length == 1) {
                    list.add("Load");
                    list.add("Save");
                }

                return list;
            }
        }
        return null;
    }
}
