package net.endrealm.minecraft.crafting.api;

import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import net.endrealm.minecraft.crafting.api.recipe.RecipeRegistry;
import net.endrealm.minecraft.crafting.api.source.CraftingSource;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.CraftingStationFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public interface CraftingRealm {
    void registerFactory(CraftingStationFactory factory);
    Optional<CraftingStationFactory> getFactory(String id);
    RecipeRegistry getRecipeRegistry();

    CraftingInventory createAndBindInventory(CraftingStation craftingStation);

    void openFactory(String stationId, UUID playerId, @Nullable CraftingSource source);
}
