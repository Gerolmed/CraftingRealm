package net.endrealm.minecraft.crafting.plugin.views.recipe;

import net.endrealm.minecraft.crafting.api.inventory.InventorySource;
import net.endrealm.minecraft.crafting.api.inventory.layout.Layout;
import net.endrealm.minecraft.crafting.api.inventory.layout.LayoutBuilder;
import net.endrealm.minecraft.crafting.api.utils.Pageable;
import net.endrealm.minecraft.crafting.api.utils.WrappedItemStack;
import org.bukkit.Material;


public abstract class AbstractPageableView<T> implements InventorySource {

    private final Layout layout;

    public AbstractPageableView(LayoutBuilder builder, Pageable<T> content) {
        var current = builder
                .rows(6)
                .fillBlock(WrappedItemStack.of(Material.GREEN_STAINED_GLASS_PANE));

        buildContent(current, content);

        layout = current.build();
    }

    protected abstract void buildContent(LayoutBuilder current, Pageable<T> content);

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
