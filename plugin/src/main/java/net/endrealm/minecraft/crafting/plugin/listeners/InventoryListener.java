package net.endrealm.minecraft.crafting.plugin.listeners;

import lombok.Data;
import net.endrealm.minecraft.crafting.plugin.player.CraftingPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

@Data
public class InventoryListener implements Listener {

    private final CraftingPlayerManager playerManager;

    @EventHandler
    public void click(InventoryClickEvent event) {
        var player = playerManager.get(event.getWhoClicked().getUniqueId());
        if(player.getInventory() == null) return;
        if(event.getClickedInventory() == null) return;
        if(!player.getInventory().matches(event.getClickedInventory())) return;
        player.getInventory().onClick(event);
    }


    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void click(InventoryInteractEvent event) {
        var player = playerManager.get(event.getWhoClicked().getUniqueId());
        if(player.getInventory() == null) return;
    }
}
