package net.endrealm.minecraft.crafting.api.recipe;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;

@Data
public class VariationIngredient implements Ingredient {

    private final WrappedItemStack[] stacks;
    private final int amount;
    @Override
    public boolean moreOrEqual(WrappedItemStack value) {

        var match = false;
        for (WrappedItemStack stack : stacks) {
            if(stack.isSimilar(value)) match = true;
        }

        return match && value.getAmount() <= getAmount();
    }

    @Override
    public boolean isSimilar(WrappedItemStack value) {
        for (WrappedItemStack stack : stacks) {
            if(stack.isSimilar(value)) return true;
        }
        return false;
    }
}
