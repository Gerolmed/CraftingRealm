package net.endrealm.minecraft.crafting.plugin.listeners;

import lombok.Data;
import net.endrealm.minecraft.crafting.plugin.player.CraftingPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@Data
public class JoinLeaveListener implements Listener {

    private final CraftingPlayerManager playerManager;

    @EventHandler
    public void join(PlayerJoinEvent event) {
        join(event.getPlayer());
    }

    @EventHandler
    public void leave(PlayerKickEvent event) {
        leave(event.getPlayer());
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        leave(event.getPlayer());
    }



    private void join(Player player) {
        playerManager.add(player.getUniqueId());
    }
    private void leave(Player player) {
        playerManager.remove(player.getUniqueId());
    }
}
