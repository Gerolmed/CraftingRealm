package net.endrealm.minecraft.crafting.stations;

import net.endrealm.minecraft.crafting.api.CraftingRealm;
import net.endrealm.minecraft.crafting.stations.stations.WorkbenchStationFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingStationPlugin extends JavaPlugin {

    private CraftingRealm plugin;

    @Override
    public void onEnable() {
        plugin = (CraftingRealm) Bukkit.getPluginManager().getPlugin("CraftingRealm");
        plugin.registerFactory(new WorkbenchStationFactory());
    }
}
