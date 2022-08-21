package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface CraftingStation {
    Layout getLayout();
    void handleUpdate(int index);
    CraftingPlayer getPlayer();

    void close();

    void executePickup(int index, boolean shiftClick, InventoryClickEvent event);
}
