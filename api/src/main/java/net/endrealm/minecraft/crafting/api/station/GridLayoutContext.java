package net.endrealm.minecraft.crafting.api.station;

import lombok.Data;

@Data
public class GridLayoutContext implements LayoutContext{
    private final int width;
    private final int height;

    @Override
    public int getInputCount() {
        return width * height;
    }
}
