package net.endrealm.minecraft.crafting.api.inventory.layout;

import net.endrealm.minecraft.crafting.api.inventory.layout.types.LayoutSlot;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.jetbrains.annotations.Nullable;

public interface LayoutBuilder {
    LayoutBuilder rows(int amount);
    LayoutBuilder addSlot(LayoutSlot slot);
    LayoutBuilder fillBlock( @Nullable WrappedItemStack startValue);
    LayoutBuilder addBlock(int index, @Nullable WrappedItemStack startValue);
    LayoutBuilder addButton(int index, WrappedItemStack startValue, Runnable onClick);
    LayoutBuilder addInput(int index, String identifier);
    LayoutBuilder addInput(int index, String identifier, @Nullable WrappedItemStack startValue);
    LayoutBuilder addOutput(int index, String identifier);
    LayoutBuilder addOutput(int index, String identifier, @Nullable WrappedItemStack startValue);
    Layout build();
}
