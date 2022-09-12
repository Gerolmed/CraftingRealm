package net.endrealm.minecraft.crafting.api.inventory;

import net.endrealm.minecraft.crafting.api.inventory.layout.Layout;

public interface InventorySource {

    /**
     * @return the layout of the rendered inventory
     */
    Layout getLayout();

    /**
     * Invoked when station is closed
     */
    void close();

    /**
     *
     * @param index the slot index or -1 if update is not associated to a specific slot
     */
    void handleUpdate(int index);

}
