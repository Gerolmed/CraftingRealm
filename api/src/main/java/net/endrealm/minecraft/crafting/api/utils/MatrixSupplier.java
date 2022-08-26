package net.endrealm.minecraft.crafting.api.utils;

@FunctionalInterface
public interface MatrixSupplier<T> {
    T[][] get(int x, int y);
}
