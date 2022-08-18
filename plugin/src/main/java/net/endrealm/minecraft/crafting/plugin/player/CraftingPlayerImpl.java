package net.endrealm.minecraft.crafting.plugin.player;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Data
public class CraftingPlayerImpl implements CraftingPlayer {
    private final UUID uuid;
    private CraftingInventory inventory;
    public Player getBukkit() {
        return Bukkit.getPlayer(uuid);
    }

    public void setInventory(CraftingInventory inventory) {
        if(inventory == getInventory()) return;
        closeInventory();
        this.inventory = inventory;
    }

    @Override
    public void closeInventory() {
        if(inventory == null) return;
        inventory.close();
        inventory = null;
    }

}
