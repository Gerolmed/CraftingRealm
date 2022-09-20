package net.endrealm.minecraft.crafting.api.utils;

import java.util.List;

public interface Pageable<T> {
    int page();
    boolean hasNext();
    boolean hasPrevious();
    List<T> section();
}
