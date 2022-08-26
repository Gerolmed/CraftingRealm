package net.endrealm.minecraft.crafting.plugin.listeners;

import lombok.Data;
import net.endrealm.minecraft.crafting.plugin.player.CraftingPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class InventoryListener implements Listener {

    private final CraftingPlayerManager playerManager;
    private final JavaPlugin plugin;

    @EventHandler
    public void click(InventoryClickEvent event) {
        var player = playerManager.get(event.getWhoClicked().getUniqueId());
        if(player.getInventory() == null) return;
        if(event.getClickedInventory() == null) return;
        if(!player.getInventory().matches(event.getClickedInventory())) return;
        player.getInventory().onClick(event);
    }


    @EventHandler()
    public void drag(InventoryDragEvent event) {
        var player = playerManager.get(event.getWhoClicked().getUniqueId());
        if(player.getInventory() == null) return;
        Bukkit.getScheduler().runTask(plugin, () -> player.getInventory().update());
    }
}
