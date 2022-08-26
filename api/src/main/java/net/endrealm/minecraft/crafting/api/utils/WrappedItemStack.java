package net.endrealm.minecraft.crafting.api.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WrappedItemStack {
    private final ItemStack itemStack;

    private WrappedItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static WrappedItemStack of(ItemStack stack) {
        return new WrappedItemStack(stack);
    }
    public static WrappedItemStack of(Material material) {
        return of(new ItemStack(material));
    }

    /**
     * Checks if this item is similar to the other and has equal or larger amount
     * @param value
     * @return
     */
    public boolean moreOrEqual(WrappedItemStack value) {
        var other = value.getItemStack();
        if(!other.isSimilar(itemStack)) return false;
        return other.getAmount() <= itemStack.getAmount();
    }
}
