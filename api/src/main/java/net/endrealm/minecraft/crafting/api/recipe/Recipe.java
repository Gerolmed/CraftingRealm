package net.endrealm.minecraft.crafting.api.recipe;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.InputSlot;
import net.endrealm.minecraft.crafting.api.station.LayoutContext;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public interface Recipe {
    Map<String, WrappedItemStack> getShape(List<InputSlot> slots, LayoutContext context);
    Map<String, WrappedItemStack> produceOutputs(CraftingStation station, CraftingPlayer player);
    boolean supports(LayoutContext context);
    String getStationId();
}
