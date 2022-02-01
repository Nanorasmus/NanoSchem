package me.nanorasmus;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SelectionManager implements Listener {

    public HashMap<UUID, Pair<Location, Location>> selections = new HashMap<>();

    Main main;

    public SelectionManager(Main main) {
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (selections.containsKey(e.getPlayer().getUniqueId())) selections.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getPlayer().isOp() && e.getClickedBlock() != null && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.ARROW) {
            UUID p = e.getPlayer().getUniqueId();
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (selections.containsKey(p)) {
                    selections.put(p, Pair.of(e.getClickedBlock().getLocation(), selections.get(p).right()));
                } else selections.put(p, Pair.of(e.getClickedBlock().getLocation(), null));
                e.getPlayer().sendMessage("Saved Pos1");
                e.setCancelled(true);
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (selections.containsKey(p)) {
                    selections.put(p, Pair.of(selections.get(p).left(), e.getClickedBlock().getLocation()));
                } else selections.put(p, Pair.of(null, e.getClickedBlock().getLocation()));
                e.getPlayer().sendMessage("Saved Pos2");
                e.setCancelled(true);
            }
        }
    }
}
