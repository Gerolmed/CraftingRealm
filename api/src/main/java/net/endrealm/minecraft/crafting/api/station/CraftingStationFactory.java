package net.endrealm.minecraft.crafting.api.station;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface CraftingStationFactory {
    String getId();

    /**
     * Create a crafting station. If opened via a block a source location may be given. Note that this value can be null if opened through a different source.
     * @param player
     * @param source
     * @return
     */
    CraftingStation produce(Player player, @Nullable Location source);
}
