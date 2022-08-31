package net.endrealm.minecraft.crafting.api.recipe;

import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;

public interface Ingredient {
    /**
     * Checks if this item is similar to the other and has equal or larger amount
     * @param value
     * @return
     */
    boolean moreOrEqual(WrappedItemStack value);

    boolean isSimilar(WrappedItemStack wrappedItemStack);
    int getAmount();
}
