package net.endrealm.minecraft.crafting.stations.stations;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.Layout;
import net.endrealm.minecraft.crafting.api.station.LayoutBuilder;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.Material;

@Data
public class WorkbenchStation implements CraftingStation {

    private final Layout layout;
    private final CraftingPlayer craftingPlayer;

    public WorkbenchStation(LayoutBuilder builder, CraftingPlayer craftingPlayer) {
        this.craftingPlayer = craftingPlayer;
        this.layout = builder
                .rows(5)
                .fillBlock(WrappedItemStack.of(Material.GREEN_STAINED_GLASS_PANE))
                .build();
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public void handleUpdate(int index) {

    }

    @Override
    public CraftingPlayer getPlayer() {
        return craftingPlayer;
    }

    @Override
    public void close() {

    }
}
