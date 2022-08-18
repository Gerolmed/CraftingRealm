package net.endrealm.minecraft.crafting.api.station;

import java.util.Collection;
import java.util.List;

public interface Layout {
    Collection<LayoutSlot> getSlots();
    int getRows();

    LayoutSlot getSlot(int slot);
}
