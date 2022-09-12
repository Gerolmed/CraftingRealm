package net.endrealm.minecraft.crafting.api.inventory.layout;

import net.endrealm.minecraft.crafting.api.inventory.layout.types.LayoutSlot;

import java.util.Collection;

public interface Layout {
    Collection<LayoutSlot> getSlots();
    int getRows();

    LayoutSlot getSlot(int slot);
}
