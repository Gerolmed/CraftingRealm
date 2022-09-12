package net.endrealm.minecraft.crafting.plugin.impl;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.inventory.InventorySource;
import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import net.endrealm.minecraft.crafting.api.inventory.layout.Layout;
import net.endrealm.minecraft.crafting.api.inventory.layout.types.ReceiveInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class CraftingInventoryImpl implements CraftingInventory {
    private final Inventory inventory;
    private final InventorySource station;
    private final Layout layout;

    public CraftingInventoryImpl(InventorySource station, CraftingPlayer player) {
        this.station = station;
        layout = station.getLayout();
        inventory = Bukkit.createInventory(null, 9 * layout.getRows(), "TEEEEEEEEEST");

        bind();

        player.setInventory(this);
        player.getBukkit().openInventory(inventory);
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

    @Override
    public void update() {
        station.handleUpdate(-1);
    }
}
