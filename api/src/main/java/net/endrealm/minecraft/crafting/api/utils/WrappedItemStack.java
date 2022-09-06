package net.endrealm.minecraft.crafting.api.utils;

import net.endrealm.minecraft.crafting.api.recipe.Ingredient;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WrappedItemStack implements Ingredient {
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


    public boolean moreOrEqual(WrappedItemStack value) {
        if(!isSimilar(value)) return false;
        return value.getAmount() >= itemStack.getAmount();
    }

    public boolean isSimilar(WrappedItemStack wrappedItemStack) {
        return getItemStack().isSimilar(wrappedItemStack.getItemStack());
    }

    @Override
    public int getAmount() {
        return itemStack.getAmount();
    }
}
