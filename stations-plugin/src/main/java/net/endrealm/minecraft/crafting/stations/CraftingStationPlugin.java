package net.endrealm.minecraft.crafting.stations;

import net.endrealm.minecraft.crafting.api.CraftingRealm;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftingStationPlugin extends JavaPlugin {

    private CraftingRealm plugin;

    @Override
    public void onEnable() {
        plugin = (CraftingRealm) Bukkit.getPluginManager().getPlugin("CraftingRealm");
    }
}
