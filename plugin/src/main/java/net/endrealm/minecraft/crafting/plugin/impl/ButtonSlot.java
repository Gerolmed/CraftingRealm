package net.endrealm.minecraft.crafting.plugin.impl;

import net.endrealm.minecraft.crafting.api.inventory.InventorySource;
import net.endrealm.minecraft.crafting.api.inventory.layout.types.LayoutSlot;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ButtonSlot implements LayoutSlot {

    private final int index;
    private WrappedItemStack value;
    private final Runnable onClick;

    public ButtonSlot(int index, WrappedItemStack startValue, Runnable onClick) {
        this.index = index;
        value = startValue;
        this.onClick = onClick;
    }

    @Override
    public Optional<WrappedItemStack> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public void setValue(@Nullable WrappedItemStack stack) {
        value = stack;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void handleClick(InventoryClickEvent event, InventorySource station) {
        event.setCancelled(true);

        if(event.getClick() == ClickType.LEFT) {
            onClick.run();
        }

    }
}
