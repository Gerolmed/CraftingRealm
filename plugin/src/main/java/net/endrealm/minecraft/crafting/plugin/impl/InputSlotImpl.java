package net.endrealm.minecraft.crafting.plugin.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.InputSlot;
import net.endrealm.minecraft.crafting.api.station.ReceiveInventory;
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
    public void handleClick(InventoryClickEvent event, CraftingStation station) {
        // delay so inventory update has been executed
        Bukkit.getScheduler().runTask(CraftingPluginImpl.getInstance(), () -> station.handleUpdate(index));
    }
}
