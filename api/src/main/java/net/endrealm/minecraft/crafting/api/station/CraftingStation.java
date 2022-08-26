package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * The core of a station. Handles all deeper logic like recipes and outputs.
 */
public interface CraftingStation {

    /**
     * @return the layout of the rendered inventory
     */
    Layout getLayout();

    /**
     *
     * @param index the slot index or -1 if update is not associated to a specific slot
     */
    void handleUpdate(int index);

    /**
     * @return the currently associated player
     */
    CraftingPlayer getPlayer();

    /**
     * Invoked when station is closed
     */
    void close();

    /**
     * Invoked when an output slot is clicked
     *
     * @param index the slot index
     * @param shiftClick is shift click
     * @param event event instance to access extended data
     */
    void executePickup(int index, boolean shiftClick, InventoryClickEvent event);
}
