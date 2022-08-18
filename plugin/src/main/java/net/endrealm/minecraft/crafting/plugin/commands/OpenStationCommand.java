package net.endrealm.minecraft.crafting.plugin.commands;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.CraftingRealm;
import net.endrealm.minecraft.crafting.plugin.impl.LayoutBuilderImpl;
import net.endrealm.minecraft.crafting.plugin.player.CraftingPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Data
public class OpenStationCommand implements CommandExecutor {

    private final CraftingRealm realm;
    private final CraftingPlayerManager playerManager;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 1) return false;
        if(!(sender instanceof Player player)) return true;
        var station = args[0];

        var factory = realm.getFactory(station);
        if(factory.isEmpty()) {
            player.sendMessage("not existing");
            return false;
        }

        var craftingStation = factory.get().produce(playerManager.get(player.getUniqueId()), null, new LayoutBuilderImpl());
        realm.createAndBindInventory(craftingStation);
        return true;
    }
}
