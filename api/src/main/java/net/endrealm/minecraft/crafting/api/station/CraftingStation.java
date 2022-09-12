package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.inventory.InventorySource;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * The core of a station. Handles all deeper logic like recipes and outputs.
 */
public interface CraftingStation extends InventorySource {

    /**
     * Invoked when an output slot is clicked
     *
     * @param index the slot index
     * @param shiftClick is shift click
     * @param event event instance to access extended data
     */
    void executePickup(int index, boolean shiftClick, InventoryClickEvent event);
}
