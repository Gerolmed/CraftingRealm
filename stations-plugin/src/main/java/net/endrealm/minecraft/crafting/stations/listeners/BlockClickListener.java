package net.endrealm.minecraft.crafting.stations.listeners;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingRealm;
import net.endrealm.minecraft.crafting.stations.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class BlockClickListener implements Listener {
    private final CraftingRealm realm;
    private final JavaPlugin plugin;

    @EventHandler
    public void click(PlayerInteractEvent event) {
        var block = event.getClickedBlock();
        if(block == null) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(block.getType() != Material.CRAFTING_TABLE) return;
        if(event.isBlockInHand() && event.getPlayer().isSneaking()) return;
        event.setCancelled(true);
        realm.openFactory(Constants.CRAFTING_TABLE, event.getPlayer().getUniqueId(), null);
    }
}
