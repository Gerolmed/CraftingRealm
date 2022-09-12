package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.inventory.layout.LayoutBuilder;
import net.endrealm.minecraft.crafting.api.recipe.book.RecipeViewData;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public interface CraftingStationFactory {
    String getId();

    /**
     * Create a crafting station. If opened via a block a source location may be given. Note that this value can be null if opened through a different source.
     * @param player the player viewing the station
     * @param source the block source or null
     * @param builder the layout builder
     * @return a station instance
     */
    CraftingStation produce(CraftingPlayer player, @Nullable Location source, LayoutBuilder builder);
    CraftingStation produceBookRecipe(CraftingPlayer player, LayoutBuilder builder, RecipeViewData data);
}
