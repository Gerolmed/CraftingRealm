package net.endrealm.minecraft.crafting.plugin.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.endrealm.minecraft.crafting.api.inventory.InventorySource;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.OutputSlot;
import net.endrealm.minecraft.crafting.api.station.ReceiveInventory;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

@Data
@AllArgsConstructor
public class OutputSlotImpl implements OutputSlot, ReceiveInventory {

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

        if(!event.isLeftClick()) {
            event.setCancelled(true);
            return;
        }

        if(event.getAction() != InventoryAction.PICKUP_ALL &&
                event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY &&
                event.getAction() != InventoryAction.PLACE_ALL &&
                event.getAction() != InventoryAction.PLACE_ONE) {
            event.setCancelled(true);
            return;
        }

        var value = getValue();
        if(value.isEmpty()) {
            event.setCancelled(true);
            return;
        }

        ((CraftingStation) station).executePickup(index, event.isShiftClick(), event);
    }
}
