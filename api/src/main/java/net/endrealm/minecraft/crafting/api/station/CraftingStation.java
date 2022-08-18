package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;

public interface CraftingStation {
    Layout getLayout();
    void handleUpdate(int index);
    CraftingPlayer getPlayer();

    void close();
}
