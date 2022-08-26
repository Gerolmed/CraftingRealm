package net.endrealm.minecraft.crafting.plugin.impl;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.station.Layout;
import net.endrealm.minecraft.crafting.api.station.LayoutSlot;

import java.util.Collection;
import java.util.Map;

@Data
public class LayoutImpl implements Layout {

    private final Map<Integer, LayoutSlot> slots;
    private final int rows;


    public Collection<LayoutSlot> getSlots() {
        return slots.values();
    }

    @Override
    public LayoutSlot getSlot(int slot) {
        return slots.get(slot);
    }
}
