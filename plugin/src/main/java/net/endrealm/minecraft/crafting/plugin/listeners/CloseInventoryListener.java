package net.endrealm.minecraft.crafting.plugin.listeners;

import lombok.Data;
import net.endrealm.minecraft.crafting.plugin.player.CraftingPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Data
public class CloseInventoryListener implements Listener {

    private final CraftingPlayerManager playerManager;

    @EventHandler
    public void close(InventoryCloseEvent event) {
        var player = playerManager.get(event.getPlayer().getUniqueId());
        if(player.getInventory() == null) return;
        if(!player.getInventory().matches(event.getInventory())) return;
        player.closeInventory();
    }
}
