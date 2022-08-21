package net.endrealm.minecraft.crafting.plugin.impl;

import net.endrealm.minecraft.crafting.api.recipe.Recipe;
import net.endrealm.minecraft.crafting.api.recipe.RecipeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeRegistryImpl implements RecipeRegistry {
    private final Map<String, List<Recipe>> recipes = new HashMap<>();
    @Override
    public List<Recipe> getRecipes(String station) {
        return recipes.getOrDefault(station, new ArrayList<>());
    }

    @Override
    public RecipeRegistry register(Recipe... recipes) {
        if(recipes == null) return this;
        for (Recipe recipe : recipes) {
            this.recipes.computeIfAbsent(recipe.getStationId(), (key) -> new ArrayList<>()).add(recipe);
        }
        return this;
    }
}
