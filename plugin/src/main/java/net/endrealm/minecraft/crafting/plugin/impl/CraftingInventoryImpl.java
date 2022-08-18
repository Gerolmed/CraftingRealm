package net.endrealm.minecraft.crafting.plugin.impl;

import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import net.endrealm.minecraft.crafting.api.station.Layout;
import net.endrealm.minecraft.crafting.api.station.ReceiveInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class CraftingInventoryImpl implements CraftingInventory {
    private final Inventory inventory;
    private final CraftingStation station;
    private final Layout layout;

    public CraftingInventoryImpl(CraftingStation station) {
        this.station = station;
        layout = station.getLayout();
        inventory = Bukkit.createInventory(null, 9 * layout.getRows(), "TEEEEEEEEEST");

        bind();

        station.getPlayer().setInventory(this);
        station.getPlayer().getBukkit().openInventory(inventory);
    }

    private void bind() {
        station.getLayout().getSlots().forEach(layoutSlot -> {

            if(layoutSlot instanceof ReceiveInventory) {
                ((ReceiveInventory) layoutSlot).setInventory(inventory);
            }

            var stack = layoutSlot.getValue();
            stack.ifPresent(wrappedItemStack -> inventory.setItem(layoutSlot.getIndex(), wrappedItemStack.getItemStack()));
            //TODO: subscribe to change
        });
    }

    @Override
    public boolean matches(Inventory inventory) {
        return inventory == this.inventory;
    }

    @Override
    public void close() {
        station.close();
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        var slot = layout.getSlot(event.getSlot());
        if(slot == null) return;
        slot.handleClick(event, station);
    }
}
