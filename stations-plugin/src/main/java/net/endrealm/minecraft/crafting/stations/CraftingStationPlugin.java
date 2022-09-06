package net.endrealm.minecraft.crafting.stations;

import net.endrealm.minecraft.crafting.api.CraftingRealm;
import net.endrealm.minecraft.crafting.api.recipe.*;
import net.endrealm.minecraft.crafting.api.utils.MatrixUtils;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import net.endrealm.minecraft.crafting.stations.listeners.BlockClickListener;
import net.endrealm.minecraft.crafting.stations.stations.WorkbenchStationFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.Optional;

public class CraftingStationPlugin extends JavaPlugin {

    private CraftingRealm craftingRealm;

    @Override
    public void onEnable() {
        craftingRealm = (CraftingRealm) Bukkit.getPluginManager().getPlugin("CraftingRealm");
        assert craftingRealm != null;
        craftingRealm.registerFactory(new WorkbenchStationFactory(this, craftingRealm.getRecipeRegistry()));
        craftingRealm.getRecipeRegistry().register(
                new SimpleRecipe(
                        Constants.CRAFTING_TABLE,
                        Collections.singletonMap("0", WrappedItemStack.of(new ItemStack(Material.STONE))),
                        Collections.singletonMap(Constants.PRIMARY_OUTPUT, WrappedItemStack.of(new ItemStack(Material.NETHER_BRICK)))
                ),
                new SimpleGridRecipe(
                        Constants.CRAFTING_TABLE,
                        new WrappedItemStack[][] {
                                {WrappedItemStack.of(new ItemStack(Material.STONE)), WrappedItemStack.of(new ItemStack(Material.STONE))},
                                {null, WrappedItemStack.of(new ItemStack(Material.STONE))}
                        },
                        Collections.singletonMap(Constants.PRIMARY_OUTPUT, WrappedItemStack.of(new ItemStack(Material.SHEEP_SPAWN_EGG)))
                ),
                new SimpleShapelessRecipe(
                        Constants.CRAFTING_TABLE,
                        new WrappedItemStack[]{
                                WrappedItemStack.of(new ItemStack(Material.CHARCOAL)),
                                WrappedItemStack.of(new ItemStack(Material.FLINT))
                        },
                        Collections.singletonMap(Constants.PRIMARY_OUTPUT, WrappedItemStack.of(new ItemStack(Material.COAL_BLOCK)))
                ),
                new SimpleShapelessRecipe(
                        Constants.CRAFTING_TABLE,
                        new Ingredient[]{
                                new VariationIngredient(new WrappedItemStack[]{
                                        WrappedItemStack.of(new ItemStack(Material.CHARCOAL)),
                                        WrappedItemStack.of(new ItemStack(Material.COAL)),
                                }, 1),
                                new VariationIngredient(new WrappedItemStack[]{
                                        WrappedItemStack.of(new ItemStack(Material.CHARCOAL)),
                                        WrappedItemStack.of(new ItemStack(Material.COAL))
                                }, 1)
                        },
                        Collections.singletonMap(Constants.PRIMARY_OUTPUT, WrappedItemStack.of(new ItemStack(Material.COAL_BLOCK)))
                )
        );

        registerVanillaRecipes();

        getServer().getPluginManager().registerEvents(new BlockClickListener(craftingRealm, this), this);
    }

    private void registerVanillaRecipes() {
        var iterator = Bukkit.getServer().recipeIterator();

        while (iterator.hasNext()) {
            var recipe = iterator.next();
            extractVanillaRecipe(recipe).ifPresent(mappedRecipe -> craftingRealm.getRecipeRegistry().register(mappedRecipe));
        }

    }

    private Optional<net.endrealm.minecraft.crafting.api.recipe.Recipe> extractVanillaRecipe(Recipe recipe) {
        if(recipe instanceof ShapelessRecipe shapelessRecipe) {
            return Optional.of(
                    new SimpleShapelessRecipe(
                            Constants.CRAFTING_TABLE,
                            shapelessRecipe.getIngredientList().stream().map(WrappedItemStack::of).toArray(WrappedItemStack[]::new),
                            Collections.singletonMap(Constants.PRIMARY_OUTPUT,WrappedItemStack.of(shapelessRecipe.getResult()))
                    )
            );
        } else if(recipe instanceof ShapedRecipe shapedRecipe) {
            var shape = shapedRecipe.getShape();
            var grid = new WrappedItemStack[shape.length][shape[0].length()];
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    var value = shapedRecipe.getIngredientMap().get(shape[y].charAt(x));
                    if(value != null) grid[y][x] = WrappedItemStack.of(value);
                }
            }
            return Optional.of(
                    new SimpleGridRecipe(
                            Constants.CRAFTING_TABLE,
                            MatrixUtils.cutMinimal(grid, (x, y) -> new WrappedItemStack[x][y]),
                            Collections.singletonMap(Constants.PRIMARY_OUTPUT,WrappedItemStack.of(shapedRecipe.getResult()))
                    )
            );
        }
        return Optional.empty();
    }
}
