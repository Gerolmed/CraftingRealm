package net.endrealm.minecraft.crafting.api.recipe;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.GridLayoutContext;
import net.endrealm.minecraft.crafting.api.station.InputSlot;
import net.endrealm.minecraft.crafting.api.station.LayoutContext;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class SimpleShapelessRecipe implements Recipe {

    private final String stationId;
    private final WrappedItemStack[] items;
    private final Map<String, WrappedItemStack> outputs;

    @Override
    public Map<String, WrappedItemStack> getShape(List<InputSlot> slots, LayoutContext context) {

        var sortedSlots = slots.stream()
                .sorted(Comparator.comparingInt(o -> -o.getValue().map(wrappedItemStack -> wrappedItemStack.getItemStack().getAmount()).orElse(0)))
                .collect(Collectors.toCollection(ArrayList::new));
        var outputs = new HashMap<String, WrappedItemStack>();
        var leftovers = new ArrayList<WrappedItemStack>();
        Arrays.stream(items)
                .sorted(Comparator.comparingInt(o -> -o.getItemStack().getAmount()))
                .forEach(wrappedItemStack -> {
                    for (int i = 0; i < sortedSlots.size(); i++) {
                        var slot = sortedSlots.get(i);
                        var value = slot.getValue();

                        // Empty slot -> as sorted by count rest must be empty as well
                        if(value.isEmpty()) {
                            outputs.put(slot.getIdentifier(), wrappedItemStack);
                            sortedSlots.remove(i);
                            return;
                        }
                        var otherItem = value.get();

                        if(!otherItem.isSimilar(wrappedItemStack)) continue;

                        outputs.put(slot.getIdentifier(), wrappedItemStack);
                        sortedSlots.remove(i);
                        return;
                    }
                    // Couldn't be filled to any empty or matching slot
                    leftovers.add(wrappedItemStack);
                });

        // Fill in remaining to any left slots
        leftovers.forEach(wrappedItemStack -> {
            // there should always be enough slots as otherwise #supports would prevent this methods execution
            var slot = sortedSlots.remove(0);
            outputs.put(slot.getIdentifier(), wrappedItemStack);
        });

        return outputs;
    }

    @Override
    public Map<String, WrappedItemStack> produceOutputs(CraftingStation station, CraftingPlayer player) {
        return outputs;
    }

    @Override
    public boolean supports(LayoutContext context) {
        return context.getInputCount() >= items.length;
    }

    @Override
    public boolean isShapeless() {
        return true;
    }
}
