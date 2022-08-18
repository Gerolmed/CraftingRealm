package net.endrealm.minecraft.crafting.api.station;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface CraftingStationFactory {
    String getId();

    /**
     * Create a crafting station. If opened via a block a source location may be given. Note that this value can be null if opened through a different source.
     * @param player
     * @param source
     * @param builder
     * @return
     */
    CraftingStation produce(CraftingPlayer player, @Nullable Location source, LayoutBuilder builder);
}
