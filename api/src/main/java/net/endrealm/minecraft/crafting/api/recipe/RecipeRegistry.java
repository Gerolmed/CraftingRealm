package net.endrealm.minecraft.crafting.api.recipe;

import java.util.List;

public interface RecipeRegistry {
    List<Recipe> getRecipes(String station);
    RecipeRegistry register(Recipe... recipes);
}
