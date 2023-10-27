package net.endrealm.minecraft.crafting.api.utils;

import java.util.List;
import java.util.Optional;

public interface Pageable<T> {
    int page();

    boolean hasNext();

    Optional<Pageable<T>> next();

    boolean hasPrevious();

    Optional<Pageable<T>> previous();


    List<T> section();
}
