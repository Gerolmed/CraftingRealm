package net.endrealm.minecraft.crafting.api.recipe.book;

import net.endrealm.minecraft.crafting.api.recipe.Recipe;

import java.util.List;

public interface RecipeViewData {
    boolean hasNext();
    boolean hasPrevious();

    RecipeViewData next();
    RecipeViewData previous();

    List<Recipe> alternatives();
    int index();
    Recipe current();
    RecipeViewData withAlternative(int index);
}
