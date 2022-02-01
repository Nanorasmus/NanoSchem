package me.nanorasmus;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public HashMap<UUID, List<Location>> selections = new HashMap<>();

    public SaveManager saveManager;

    public CommandManager commandManager;
    public TabCompletion tabCompletion;

    public SelectionManager selectionManager;

    @Override
    public void onEnable() {
        try {
            saveManager = new SaveManager(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        commandManager = new CommandManager(this);
        tabCompletion = new TabCompletion(this);

        selectionManager = new SelectionManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
