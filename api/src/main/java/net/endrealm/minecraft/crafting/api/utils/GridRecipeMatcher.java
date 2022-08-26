package net.endrealm.minecraft.crafting.api.utils;

import net.endrealm.minecraft.crafting.api.recipe.Recipe;
import net.endrealm.minecraft.crafting.api.recipe.RecipeRegistry;
import net.endrealm.minecraft.crafting.api.station.GridLayoutContext;
import net.endrealm.minecraft.crafting.api.station.InputSlot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        var inputMap = optimizedInputs();
        var result = supportedTables.stream().flatMap(id -> recipeRegistry.getRecipes(id).stream()).iterator();
        while (result.hasNext()) {
            var recipe = result.next();
            if(match(recipe, inputMap)) return Optional.of(recipe);
        }

        return Optional.empty();
    }

    private Map<String, WrappedItemStack> optimizedInputs() {
        var shape = new WrappedItemStack[context.getHeight()][context.getWidth()];
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                shape[y][x] = slotList.get(y * context.getWidth() + x).getValue().orElse(null);
            }
        }

        shape = cutMinimal(shape);

        var finalShape = new HashMap<String, WrappedItemStack>();

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                var stack = shape[y][x];
                if(stack == null) continue;
                finalShape.put(slotList.get(y * context.getWidth() + x).getIdentifier(), stack);
            }
        }
        return finalShape;
    }

    private WrappedItemStack[][] cutMinimal(WrappedItemStack[][] shape) {

        boolean preCheck = true;
        int preRowCount = 0;
        int postRowCount = 0;

        // Check for empty rows
        for (int y = 0; y < shape.length; y++) {
            var empties = 0;
            for (int x = 0; x < shape[y].length; x++) {
                var stack = shape[y][x];
                if(stack != null) break;
                empties++;
            }
            if(empties != shape[y].length) {
                postRowCount = 0;
                preCheck = false;
            } else {
                if(preCheck) preRowCount++;
                postRowCount++;
            }
        }

        // grid empty
        if(preRowCount + postRowCount >= shape.length) return new WrappedItemStack[0][];
        preCheck = true;
        int preColCount = 0;
        int postColCount = 0;

        for (int x = 0; x < shape[0].length; x++) {
            var empties = 0;
            for (int y = 0; y < shape.length; y++) {
                var stack = shape[y][x];
                if(stack != null) break;
                empties++;
            }
            if(empties != shape.length) {
                postColCount = 0;
                preCheck = false;
            } else {
                if(preCheck) preColCount++;
                postColCount++;
            }
        }

        // Build new shape
        var outShape = new WrappedItemStack[shape.length-preRowCount-postRowCount][shape[0].length-preColCount-postColCount];

        for (int y = 0; y < outShape.length; y++) {
            System.arraycopy(shape[y + preRowCount], preColCount, outShape[y], 0, outShape[y].length);
        }

        return outShape;
    }

    private boolean match(Recipe recipe, Map<String, WrappedItemStack> inputMap) {
        var shape = recipe.getShape(slotList, context);
        if(!shape.keySet().equals(inputMap.keySet())) return false;
        for (var entry : shape.entrySet()) {
            if(!inputMap.get(entry.getKey()).moreOrEqual(entry.getValue())) return false;
        }
        return true;
    }
}
