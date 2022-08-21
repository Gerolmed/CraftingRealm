package net.endrealm.minecraft.crafting.stations.stations;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.recipe.Recipe;
import net.endrealm.minecraft.crafting.api.recipe.RecipeRegistry;
import net.endrealm.minecraft.crafting.api.station.*;
import net.endrealm.minecraft.crafting.api.utils.GridRecipeMatcher;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import net.endrealm.minecraft.crafting.stations.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class WorkbenchStation implements CraftingStation {

    private final Layout layout;
    private final CraftingPlayer craftingPlayer;
    private final JavaPlugin plugin;
    private final GridRecipeMatcher matcher;

    public WorkbenchStation(LayoutBuilder builder, CraftingPlayer craftingPlayer, RecipeRegistry recipeRegistry, JavaPlugin plugin) {
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
                .addOutput(24, Constants.PRIMARY_OUTPUT)
                .build();
        this.plugin = plugin;

        var inputs = layout.getSlots().stream().filter(layoutSlot -> layoutSlot instanceof InputSlot).map(layoutSlot -> (InputSlot) layoutSlot).collect(Collectors.toList());
        matcher = new GridRecipeMatcher(List.of(Constants.CRAFTING_TABLE), inputs, new GridLayoutContext(3, 3), recipeRegistry);
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
        matcher.detect().ifPresentOrElse(this::assignOutput, this::clearOutput);
    }

    private void clearOutput() {
        getOutputs().forEach(outputSlot -> outputSlot.setValue(null));
    }

    private void assignOutput(Recipe recipe) {
        var outputs = recipe.produceOutputs(this, getPlayer());
        getOutputs().forEach(outputSlot -> outputSlot.setValue(outputs.get(outputSlot.getIdentifier())));
    }

    private Stream<OutputSlot> getOutputs() {
        return layout.getSlots().stream()
                .filter(layoutSlot -> layoutSlot instanceof OutputSlot)
                .map(layoutSlot -> (OutputSlot) layoutSlot);
    }

    @Override
    public CraftingPlayer getPlayer() {
        return craftingPlayer;
    }

    @Override
    public void close() {

    }

    @Override
    public void executePickup(int index, boolean shiftClick, InventoryClickEvent event) {
        event.setCancelled(true);
        if(shiftClick) {
            // TODO support shift click
            return;
        }

        // TODO: deduct items
        Bukkit.getScheduler().runTask(plugin, this::calculateOutput);
    }
}
