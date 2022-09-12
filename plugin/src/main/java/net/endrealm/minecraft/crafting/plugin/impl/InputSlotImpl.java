package net.endrealm.minecraft.crafting.plugin.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.endrealm.minecraft.crafting.api.inventory.InventorySource;
import net.endrealm.minecraft.crafting.api.inventory.layout.InputSlot;
import net.endrealm.minecraft.crafting.api.inventory.layout.types.ReceiveInventory;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import net.endrealm.minecraft.crafting.plugin.CraftingPluginImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

@Data
@AllArgsConstructor
public class InputSlotImpl implements InputSlot, ReceiveInventory {

    private final int index;
    private final String identifier;
    private WrappedItemStack value;

    private Inventory inventory;

    public void setValue(WrappedItemStack value) {
        this.value = value;
        if(inventory == null) return;
        inventory.setItem(index, value == null ? null : value.getItemStack());
    }

    @Override
    public Optional<WrappedItemStack> getValue() {
        return Optional.ofNullable(inventory.getItem(index)).map(WrappedItemStack::of);
    }
    @Override
    public void handleClick(InventoryClickEvent event, InventorySource station) {
        // delay so inventory update has been executed
        Bukkit.getScheduler().runTask(CraftingPluginImpl.getInstance(), () -> station.handleUpdate(index));
    }
}
