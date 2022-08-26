package net.endrealm.minecraft.crafting.api.utils;

@FunctionalInterface
public interface ValueMapper<T, V> {
    V get(T value);
}
