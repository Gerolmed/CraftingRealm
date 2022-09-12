package net.endrealm.minecraft.crafting.plugin;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import net.endrealm.minecraft.crafting.api.CraftingRealm;
import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import net.endrealm.minecraft.crafting.api.recipe.Recipe;
import net.endrealm.minecraft.crafting.api.recipe.RecipeRegistry;
import net.endrealm.minecraft.crafting.api.source.CraftingSource;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.CraftingStationFactory;
import net.endrealm.minecraft.crafting.plugin.commands.OpenStationCommand;
import net.endrealm.minecraft.crafting.plugin.impl.CraftingInventoryImpl;
import net.endrealm.minecraft.crafting.plugin.impl.LayoutBuilderImpl;
import net.endrealm.minecraft.crafting.plugin.impl.RecipeRegistryImpl;
import net.endrealm.minecraft.crafting.plugin.listeners.CloseInventoryListener;
import net.endrealm.minecraft.crafting.plugin.listeners.InventoryListener;
import net.endrealm.minecraft.crafting.plugin.listeners.JoinLeaveListener;
import net.endrealm.minecraft.crafting.plugin.player.CraftingPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CraftingPluginImpl extends JavaPlugin implements CraftingRealm {

    public static CraftingPluginImpl getInstance() {
        return instance;
    }

    private static CraftingPluginImpl instance;
    private final Map<String, CraftingStationFactory> factoryMap = new HashMap<>();
    private final CraftingPlayerManager playerManager = new CraftingPlayerManager();
    private final RecipeRegistry recipeRegistry = new RecipeRegistryImpl();
    @Override
    public void registerFactory(CraftingStationFactory factory) {
        factoryMap.put(factory.getId(), factory);
    }

    @Override
    public Optional<CraftingStationFactory> getFactory(String id) {
        return Optional.ofNullable(factoryMap.get(id));
    }

    @Override
    public RecipeRegistry getRecipeRegistry() {
        return recipeRegistry;
    }

    @Override
    public CraftingInventory createAndBindInventory(CraftingStation craftingStation, CraftingPlayer player) {
        return new CraftingInventoryImpl(craftingStation, player);
    }

    @Override
    public void openFactory(String stationId, UUID playerId, @Nullable CraftingSource source) {
        var player = playerManager.get(playerId);
        var craftingStation = getFactory(stationId).get().produce(player, null, new LayoutBuilderImpl());
        this.createAndBindInventory(craftingStation, player);
    }

    @Override
    public Optional<CraftingStationFactory> getFactoryByRecipe(Recipe recipe) {
        return getFactory(recipe.getStationId());
    }


    @Override
    public void onEnable() {
        instance = this;

        getCommand("stationopen").setExecutor(new OpenStationCommand(this, playerManager));
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(playerManager, this), this);
        getServer().getPluginManager().registerEvents(new CloseInventoryListener(playerManager), this);
    }
}
