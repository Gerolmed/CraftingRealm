package net.endrealm.minecraft.crafting.api;

import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.CraftingStationFactory;

import java.util.Optional;

public interface CraftingRealm {
    void registerFactory(CraftingStationFactory factory);
    Optional<CraftingStationFactory> getFactory(String id);

    CraftingInventory createAndBindInventory(CraftingStation craftingStation);
}
