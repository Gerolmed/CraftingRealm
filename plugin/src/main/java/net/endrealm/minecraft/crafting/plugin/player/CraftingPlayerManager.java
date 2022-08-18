package net.endrealm.minecraft.crafting.plugin.player;

import net.endrealm.minecraft.crafting.api.CraftingPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CraftingPlayerManager {

    private final Map<UUID, CraftingPlayer> playerMap = new HashMap<>();

    public void add(UUID uuid) {
        playerMap.put(uuid, new CraftingPlayerImpl(uuid));
    }
    public void remove(UUID uuid) {
        playerMap.remove(uuid);
    }
    public CraftingPlayer get(UUID uuid) {
        return playerMap.get(uuid);
    }
}
