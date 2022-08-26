package net.endrealm.minecraft.crafting.stations;

import net.endrealm.minecraft.crafting.api.CraftingRealm;
import net.endrealm.minecraft.crafting.api.recipe.SimpleGridRecipe;
import net.endrealm.minecraft.crafting.api.recipe.SimpleRecipe;
import net.endrealm.minecraft.crafting.api.recipe.SimpleShapelessRecipe;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import net.endrealm.minecraft.crafting.stations.listeners.BlockClickListener;
import net.endrealm.minecraft.crafting.stations.stations.WorkbenchStationFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class CraftingStationPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        CraftingRealm plugin = (CraftingRealm) Bukkit.getPluginManager().getPlugin("CraftingRealm");
        assert plugin != null;
        plugin.registerFactory(new WorkbenchStationFactory(this, plugin.getRecipeRegistry()));
        plugin.getRecipeRegistry().register(
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
                )
        );

        getServer().getPluginManager().registerEvents(new BlockClickListener(plugin, this), this);
    }
}
