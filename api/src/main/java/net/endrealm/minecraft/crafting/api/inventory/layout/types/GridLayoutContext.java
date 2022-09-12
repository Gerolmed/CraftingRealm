package net.endrealm.minecraft.crafting.api.inventory.layout.types;

import lombok.Data;
import net.endrealm.minecraft.crafting.api.inventory.layout.LayoutContext;

@Data
public class GridLayoutContext implements LayoutContext {
    private final int width;
    private final int height;

    @Override
    public int getInputCount() {
        return width * height;
    }
}
