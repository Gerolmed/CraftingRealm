package net.endrealm.minecraft.crafting.plugin.views.recipe;

import net.endrealm.minecraft.crafting.api.inventory.layout.LayoutBuilder;
import net.endrealm.minecraft.crafting.api.utils.Pageable;

public class ListPageableView<T> extends AbstractPageableView<T> {

    public ListPageableView(LayoutBuilder builder, Pageable<T> content) {
        super(builder, content);
    }

    @Override
    protected void buildContent(LayoutBuilder current, Pageable<T> content) {

        // current.addButton(8)
    }
}
