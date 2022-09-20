package net.endrealm.minecraft.crafting.plugin.views.recipe;

import net.endrealm.minecraft.crafting.api.inventory.InventorySource;
import net.endrealm.minecraft.crafting.api.inventory.layout.Layout;
import net.endrealm.minecraft.crafting.api.inventory.layout.LayoutBuilder;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.Material;


public class PageableView implements InventorySource {

    private final Layout layout;

    public PageableView(LayoutBuilder builder) {
        layout = builder
                .rows(6)
                .fillBlock(WrappedItemStack.of(Material.GREEN_STAINED_GLASS_PANE))
                .build();
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public void close() {

    }

    @Override
    public void handleUpdate(int index) {

    }
}
