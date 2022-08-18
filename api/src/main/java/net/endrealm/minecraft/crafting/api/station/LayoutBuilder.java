package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface LayoutBuilder {
    LayoutBuilder rows(int amount);
    LayoutBuilder addSlot(LayoutSlot slot);
    LayoutBuilder fillBlock( @Nullable WrappedItemStack startValue);
    LayoutBuilder addBlock(int index, @Nullable WrappedItemStack startValue);
    LayoutBuilder addInput(int index, @Nullable WrappedItemStack startValue);
    LayoutBuilder addOutput(int index, @Nullable WrappedItemStack startValue);
    Layout build();
}
