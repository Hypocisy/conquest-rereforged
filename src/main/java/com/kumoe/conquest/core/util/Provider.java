package com.kumoe.conquest.core.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Supplier;

public class Provider<T extends ItemLike> implements ItemLike {
    private final String name;
    private final Supplier<T> supplier;
    private final Supplier<T> defaultValue;
    private T value;

    private Provider(String name, Supplier<T> supplier, Supplier<T> defaultValue) {
        this.name = name;
        this.supplier = supplier;
        this.defaultValue = defaultValue;
    }

    public static Block block(String name) {
        return block(new ResourceLocation(name));
    }

    public static Block block(ResourceLocation name) {
        return block("" + name, () -> ForgeRegistries.BLOCKS.getValue(name));
    }

    public static Block block(String name, Supplier<net.minecraft.world.level.block.Block> getter) {
        return new Block(name, getter);
    }

    public static Item item(String name) {
        return item(new ResourceLocation(name));
    }

    public static Item item(ResourceLocation name) {
        return item("" + name, () -> ForgeRegistries.ITEMS.getValue(name));
    }

    public static Item item(String name, Supplier<net.minecraft.world.item.Item> getter) {
        return new Item(name, getter);
    }

    public T get() {
        if (this.value == null) {
            this.value = this.supplier.get();
            if (this.value == null) {
                (new NullPointerException("Invalid item: " + this.name)).printStackTrace();
                this.value = this.defaultValue.get();
            }
        }

        return this.value;
    }

    public Stack toStack() {
        return new Stack(this);
    }

    public net.minecraft.world.item.Item asItem() {
        T t = this.get();
        if (t == null) {
            throw new NullPointerException("Invalid item: " + this.name);
        } else {
            return t.asItem();
        }
    }

    public static class Stack implements Supplier<ItemStack> {
        private final ItemLike provider;

        public Stack(ItemLike provider) {
            this.provider = provider;
        }

        public ItemStack get() {
            return new ItemStack(this.provider.asItem());
        }

        public Optional<ItemStack> getSafely() {
            net.minecraft.world.item.Item item = this.provider.asItem();
            return item == Items.AIR ? Optional.empty() : Optional.of(new ItemStack(item));
        }
    }

    public static class Block extends Provider<net.minecraft.world.level.block.Block> {
        public Block(String name, Supplier<net.minecraft.world.level.block.Block> supplier) {
            super(name, supplier, () -> Blocks.AIR);
        }
    }

    public static class Item extends Provider<net.minecraft.world.item.Item> {
        public Item(String name, Supplier<net.minecraft.world.item.Item> supplier) {
            super(name, supplier, () -> Items.AIR);
        }
    }
}