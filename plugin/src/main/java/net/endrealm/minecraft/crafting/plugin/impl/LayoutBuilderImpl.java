package net.endrealm.minecraft.crafting.plugin.impl;

import net.endrealm.minecraft.crafting.api.station.Layout;
import net.endrealm.minecraft.crafting.api.station.LayoutBuilder;
import net.endrealm.minecraft.crafting.api.station.LayoutSlot;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class LayoutBuilderImpl implements LayoutBuilder {
    private final Map<Integer, LayoutSlot> slotMap = new HashMap<>();
    private int rows = 1;

    @Override
    public LayoutBuilder rows(int amount) {
        rows = amount;
        return this;
    }

    @Override
    public LayoutBuilder addSlot(LayoutSlot slot) {
        slotMap.put(slot.getIndex(), slot);
        return this;
    }

    @Override
    public LayoutBuilder fillBlock(@Nullable WrappedItemStack startValue) {
        final int width = 9;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < width; j++) {
                addSlot(new BlockedSlot(i * width + j, startValue));
            }
        }
        return this;
    }

    @Override
    public LayoutBuilder addBlock(int index, @Nullable WrappedItemStack startValue) {
        addSlot(new BlockedSlot(index, startValue));
        return this;
    }

    @Override
    public LayoutBuilder addInput(int index, String identifier) {
        return addInput(index, identifier, null);
    }
    @Override
    public LayoutBuilder addInput(int index, String identifier, @Nullable WrappedItemStack startValue) {
        addSlot(new InputSlotImpl(index, identifier, startValue, null));
        return this;
    }

    @Override
    public LayoutBuilder addOutput(int index, @Nullable WrappedItemStack startValue) {
        return this;
    }

    @Override
    public Layout build() {
        return new LayoutImpl(slotMap, rows);
    }
}
