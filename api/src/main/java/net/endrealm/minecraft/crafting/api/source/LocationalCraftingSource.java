package net.endrealm.minecraft.crafting.api.source;

import org.bukkit.Location;

public interface LocationalCraftingSource extends CraftingSource{
    Location getLocation();
}
