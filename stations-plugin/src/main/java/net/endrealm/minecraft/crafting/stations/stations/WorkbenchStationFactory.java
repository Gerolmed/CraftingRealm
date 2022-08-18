package net.endrealm.minecraft.crafting.stations.stations;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.CraftingStationFactory;
import net.endrealm.minecraft.crafting.api.station.LayoutBuilder;
import net.endrealm.minecraft.crafting.stations.Constants;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public class WorkbenchStationFactory implements CraftingStationFactory {
    @Override
    public String getId() {
        return Constants.CRAFTING_TABLE;
    }

    @Override
    public CraftingStation produce(CraftingPlayer player, @Nullable Location source, LayoutBuilder builder) {
        return new WorkbenchStation(builder, player);
    }
}
