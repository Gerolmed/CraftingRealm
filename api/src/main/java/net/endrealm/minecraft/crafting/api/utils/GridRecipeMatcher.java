package net.endrealm.minecraft.crafting.api.utils;

import net.endrealm.minecraft.crafting.api.recipe.Recipe;
import net.endrealm.minecraft.crafting.api.recipe.RecipeRegistry;
import net.endrealm.minecraft.crafting.api.inventory.layout.types.GridLayoutContext;
import net.endrealm.minecraft.crafting.api.inventory.layout.InputSlot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for matching shaped recipes on configurable grid. All recipes must fit into the grid!
 */
public class GridRecipeMatcher {
    private final List<String> supportedTables;
    private final List<InputSlot> slotList;
    private final GridLayoutContext context;
    private final RecipeRegistry recipeRegistry;

    public GridRecipeMatcher(List<String> supportedTables, List<InputSlot> slotList, GridLayoutContext context, RecipeRegistry recipeRegistry) {
        this.supportedTables = supportedTables;

        this.slotList = slotList;
        this.context = context;
        this.recipeRegistry = recipeRegistry;
    }

    public Optional<Recipe> detect() {
        var inputMap = optimizedInputs(
                value -> value.getValue().orElse(null),
                (x, y) -> new WrappedItemStack[x][y]
        );
        var rawInputMap = slotList.stream().filter(slot -> slot.getValue().isPresent()).collect(Collectors.toMap(InputSlot::getIdentifier, slot -> slot.getValue().get()));
        var result = supportedTables.stream().flatMap(id -> recipeRegistry.getRecipes(id).stream()).iterator();
        while (result.hasNext()) {
            var recipe = result.next();
            if(match(recipe, inputMap, rawInputMap)) return Optional.of(recipe);
        }

        return Optional.empty();
    }

    private <T> Map<String, T> optimizedInputs(ValueMapper<InputSlot, T> mapper, MatrixSupplier<T> supplier) {
        var shape = supplier.get(context.getHeight(), context.getWidth());
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                shape[y][x] = mapper.get(slotList.get(y * context.getWidth() + x));
            }
        }

        shape = MatrixUtils.cutMinimal(shape, supplier);

        var finalShape = new HashMap<String, T>();

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                var stack = shape[y][x];
                if(stack == null) continue;
                finalShape.put(slotList.get(y * context.getWidth() + x).getIdentifier(), stack);
            }
        }
        return finalShape;
    }

    private boolean match(Recipe recipe, Map<String, WrappedItemStack> inputMap, Map<String, WrappedItemStack> rawInputMap) {
        var shape = recipe.getShape(slotList, context);
        if(!recipe.isShapeless()) {
            if(!shape.keySet().equals(inputMap.keySet())) return false;
            for (var entry : shape.entrySet()) {
                if(!entry.getValue().moreOrEqual(inputMap.get(entry.getKey()))) return false;
            }
            return true;
        }

        if(!shape.keySet().equals(rawInputMap.keySet())) return false;
        for (var entry : shape.entrySet()) {
            if(!entry.getValue().moreOrEqual(rawInputMap.get(entry.getKey()))) return false;
        }
        return true;
    }

    public void deductCosts(Recipe recipe) {
        var inputMap = optimizedInputs(
                value -> value,
                (x, y) -> new InputSlot[x][y]
        );

        var shape = recipe.getShape(slotList, context);
        for (var entry : shape.entrySet()) {
            var ingredient = entry.getValue();
            var input = inputMap.get(entry.getKey());

            // Should never be null as "match" ran before
            var inputValue = input.getValue().orElseThrow();
            var newAmount = inputValue.getItemStack().getAmount() - ingredient.getAmount();
            if(newAmount > 0) {
                inputValue.getItemStack().setAmount(newAmount);
            } else {
                input.setValue(null);
            }
        }
    }
}
