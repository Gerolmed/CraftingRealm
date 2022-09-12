package net.endrealm.minecraft.crafting.api.recipe;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.inventory.layout.InputSlot;
import net.endrealm.minecraft.crafting.api.inventory.layout.LayoutContext;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;

import java.util.List;
import java.util.Map;

@Data
public class SimpleRecipe implements Recipe {

    private final String stationId;
    private final Map<String, Ingredient> shape;
    private final Map<String, WrappedItemStack> outputs;

    @Override
    public Map<String, Ingredient> getShape(List<InputSlot> slots, LayoutContext context) {
        return shape;
    }

    @Override
    public Map<String, WrappedItemStack> produceOutputs(CraftingStation station, CraftingPlayer player) {
        return outputs;
    }

    @Override
    public boolean supports(LayoutContext context) {
        return true;
    }

    @Override
    public boolean isShapeless() {
        return false;
    }
}
