package net.endrealm.minecraft.crafting.api.recipe;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.inventory.layout.InputSlot;
import net.endrealm.minecraft.crafting.api.inventory.layout.LayoutContext;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;

import java.util.List;
import java.util.Map;

public interface Recipe {
    Map<String, Ingredient> getShape(List<InputSlot> slots, LayoutContext context);
    Map<String, WrappedItemStack> produceOutputs(CraftingStation station, CraftingPlayer player);
    boolean supports(LayoutContext context);

    boolean isShapeless();
    String getStationId();
}
