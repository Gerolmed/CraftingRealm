package net.endrealm.minecraft.crafting.api.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public interface CraftingInventory {
    boolean matches(Inventory inventory);

    void close();

    void onClick(InventoryClickEvent event);
}
