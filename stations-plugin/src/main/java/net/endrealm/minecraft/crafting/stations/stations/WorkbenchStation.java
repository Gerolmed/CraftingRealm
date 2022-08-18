package net.endrealm.minecraft.crafting.stations.stations;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.*;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.Material;

import java.util.stream.Collectors;

@Data
public class WorkbenchStation implements CraftingStation {

    private final Layout layout;
    private final CraftingPlayer craftingPlayer;

    public WorkbenchStation(LayoutBuilder builder, CraftingPlayer craftingPlayer) {
        this.craftingPlayer = craftingPlayer;
        this.layout = builder
                .rows(5)
                .fillBlock(WrappedItemStack.of(Material.GREEN_STAINED_GLASS_PANE))
                .addInput(10, "0")
                .addInput(11, "1")
                .addInput(12, "2")
                .addInput(19, "3")
                .addInput(20, "4")
                .addInput(21, "5")
                .addInput(28, "6")
                .addInput(29, "7")
                .addInput(30, "8")
                .addOutput(24, "primary")
                .build();
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public void handleUpdate(int index) {
        if(layout.getSlot(index) instanceof InputSlot) {
            calculateOutput();
        }
    }

    private void calculateOutput() {
        var inputs = layout.getSlots().stream()
                .filter(layoutSlot -> layoutSlot instanceof InputSlot)
                .map(layoutSlot -> (InputSlot)layoutSlot)
                .collect(Collectors.toMap(InputSlot::getIdentifier, LayoutSlot::getValue));
        // TODO: check recipes and adjust output
    }

    @Override
    public CraftingPlayer getPlayer() {
        return craftingPlayer;
    }

    @Override
    public void close() {

    }
}
