package net.endrealm.minecraft.crafting.api.recipe;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.GridLayoutContext;
import net.endrealm.minecraft.crafting.api.station.InputSlot;
import net.endrealm.minecraft.crafting.api.station.LayoutContext;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SimpleGridRecipe implements Recipe {

    private final String stationId;
    private final WrappedItemStack[][] shape;
    private final Map<String, WrappedItemStack> outputs;

    @Override
    public Map<String, WrappedItemStack> getShape(List<InputSlot> slots, LayoutContext context) {
        if(!(context instanceof GridLayoutContext gridContext)) return null;
        var finalShape = new HashMap<String, WrappedItemStack>();
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                var stack = shape[y][x];
                if(stack == null) continue;
                finalShape.put(slots.get(y * gridContext.getWidth() + x).getIdentifier(), stack);
            }
        }

        return finalShape;
    }

    @Override
    public Map<String, WrappedItemStack> produceOutputs(CraftingStation station, CraftingPlayer player) {
        return outputs;
    }

    @Override
    public boolean supports(LayoutContext context) {
        return context instanceof GridLayoutContext grid && grid.getHeight() >= shape.length && grid.getWidth() >= shape[0].length;
    }

    @Override
    public boolean isShapeless() {
        return false;
    }
}
