package net.endrealm.minecraft.crafting.plugin;

import net.endrealm.minecraft.crafting.api.CraftingRealm;
import net.endrealm.minecraft.crafting.api.inventory.CraftingInventory;
import net.endrealm.minecraft.crafting.api.station.CraftingStation;
import net.endrealm.minecraft.crafting.api.station.CraftingStationFactory;
import net.endrealm.minecraft.crafting.plugin.commands.OpenStationCommand;
import net.endrealm.minecraft.crafting.plugin.impl.CraftingInventoryImpl;
import net.endrealm.minecraft.crafting.plugin.listeners.CloseInventoryListener;
import net.endrealm.minecraft.crafting.plugin.listeners.InventoryListener;
import net.endrealm.minecraft.crafting.plugin.listeners.JoinLeaveListener;
import net.endrealm.minecraft.crafting.plugin.player.CraftingPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CraftingPluginImpl extends JavaPlugin implements CraftingRealm {
    private final Map<String, CraftingStationFactory> factoryMap = new HashMap<>();
    private final CraftingPlayerManager playerManager = new CraftingPlayerManager();
    @Override
    public void registerFactory(CraftingStationFactory factory) {
        factoryMap.put(factory.getId(), factory);
    }

    @Override
    public Optional<CraftingStationFactory> getFactory(String id) {
        return Optional.ofNullable(factoryMap.get(id));
    }

    @Override
    public CraftingInventory createAndBindInventory(CraftingStation craftingStation) {
        return new CraftingInventoryImpl(craftingStation);
    }


    @Override
    public void onEnable() {
        getCommand("stationopen").setExecutor(new OpenStationCommand(this, playerManager));
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new CloseInventoryListener(playerManager), this);
    }
}
