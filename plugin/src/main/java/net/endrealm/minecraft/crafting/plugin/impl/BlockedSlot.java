package net.endrealm.minecraft.crafting.plugin.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.LayoutSlot;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Data
@AllArgsConstructor
public class BlockedSlot implements LayoutSlot {

    private final int index;
    @Nullable
    private WrappedItemStack itemStack;

    @Override
    public Optional<WrappedItemStack> getValue() {
        return Optional.ofNullable(itemStack);
    }

    @Override
    public void setValue(@Nullable WrappedItemStack stack) {
        itemStack = stack;
    }

    @Override
    public void handleClick(InventoryClickEvent event, CraftingStation station) {
        event.setCancelled(true);
    }
}
