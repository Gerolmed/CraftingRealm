package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface LayoutSlot {
    Optional<WrappedItemStack> getValue();
    void setValue(@Nullable WrappedItemStack stack);

    int getIndex();
    void handleClick(InventoryClickEvent event, CraftingStation station);
}
