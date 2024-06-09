package com.kumoe.conquest.core.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class Dummy {
    private static final Supplier<Object> empty = () -> {
        return null;
    };
    private static final Supplier<Item> item = () -> {
        return null;
    };

    public Dummy() {
    }

    public static <T> T dummy() {
        return (T) empty.get();
    }

    public static <T extends Block> T block() {
        return dummy();
    }

    public static <T extends Item> T item() {
        return dummy();
    }
}