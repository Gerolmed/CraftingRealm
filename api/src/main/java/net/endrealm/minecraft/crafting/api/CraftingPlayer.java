package net.endrealm.minecraft.crafting.api;

import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface CraftingPlayer {
    UUID getUuid();
    Player getBukkit();

    void setInventory(CraftingInventory craftingInventory);
    CraftingInventory getInventory();

    void closeInventory();
}
