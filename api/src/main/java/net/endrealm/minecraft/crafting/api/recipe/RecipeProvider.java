package net.endrealm.minecraft.crafting.api.recipe;

public interface RecipeProvider<T extends Recipe> {
    T deserialize(byte[] data);
    byte[] serialize(T recipe);
    String getType();
}
