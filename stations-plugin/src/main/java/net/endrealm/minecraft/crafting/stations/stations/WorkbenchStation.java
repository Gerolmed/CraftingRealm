package net.endrealm.minecraft.crafting.stations.stations;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.inventory.layout.*;
import net.endrealm.minecraft.crafting.api.inventory.layout.types.GridLayoutContext;
import net.endrealm.minecraft.crafting.api.inventory.layout.types.OutputSlot;
import net.endrealm.minecraft.crafting.api.recipe.Recipe;
import net.endrealm.minecraft.crafting.api.recipe.RecipeRegistry;
import net.endrealm.minecraft.crafting.api.station.*;
import net.endrealm.minecraft.crafting.api.utils.GridRecipeMatcher;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import net.endrealm.minecraft.crafting.stations.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
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
    private Recipe recipe;

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
        if(index == -1 || layout.getSlot(index) instanceof InputSlot) {
            calculateOutput();
        }
    }

    private void calculateOutput() {
        matcher.detect().ifPresentOrElse(this::assignOutput, this::clearOutput);
    }

    private void clearOutput() {
        this.recipe = null;
        getOutputs().forEach(outputSlot -> outputSlot.setValue(null));
    }

    private void assignOutput(Recipe recipe) {
        this.recipe = recipe;
        var outputs = recipe.produceOutputs(this, getPlayer());
        getOutputs().forEach(outputSlot -> outputSlot.setValue(outputs.get(outputSlot.getIdentifier())));
    }

    private Stream<OutputSlot> getOutputs() {
        return layout.getSlots().stream()
                .filter(layoutSlot -> layoutSlot instanceof OutputSlot)
                .map(layoutSlot -> (OutputSlot) layoutSlot);
    }

    public CraftingPlayer getPlayer() {
        return craftingPlayer;
    }

    @Override
    public void close() {
        var items = layout.getSlots().stream()
                .filter(slot -> slot instanceof InputSlot)
                .map(slot -> (InputSlot) slot)
                .filter(slot -> slot.getValue().isPresent())
                .map(slot -> slot.getValue().get().getItemStack())
                .toList();
        var player = getPlayer().getBukkit();
        var leftItems = player.getInventory().addItem(items.toArray(new ItemStack[0]));
        leftItems.values().forEach(itemStack -> player.getWorld().dropItem(player.getLocation(), itemStack));
    }

    @Override
    public void executePickup(int index, boolean shiftClick, InventoryClickEvent event) {
        event.setCancelled(true);
        if(shiftClick) {
            // TODO support shift click
            return;
        }
        var currentHand = event.getCursor();
        var value = getLayout().getSlot(index).getValue();

        if(value.isEmpty()) {
            event.setCancelled(true);
            return;
        }

        var outValue = value.get();

        assert currentHand != null;

        if(currentHand.getType() != Material.AIR && !outValue.getItemStack().isSimilar(currentHand)) {
            event.setCancelled(true);
            return;
        }
        var currentHandAmount = currentHand.getAmount();
        var newAmount = currentHandAmount + outValue.getItemStack().getAmount();
        if(newAmount > outValue.getItemStack().getMaxStackSize()) return;

        if(currentHand.getType() != Material.AIR) {
            currentHand.setAmount(newAmount);
        } else {
            event.setCancelled(false);
        }

        deductCosts();

        Bukkit.getScheduler().runTask(plugin, this::calculateOutput);
    }

    private void deductCosts() {
        matcher.deductCosts(recipe);
    }
}
