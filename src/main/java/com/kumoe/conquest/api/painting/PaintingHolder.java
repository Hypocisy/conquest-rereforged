package com.kumoe.conquest.api.painting;

import com.kumoe.conquest.api.painting.art.Art;
import net.minecraft.world.item.ItemStack;

public interface PaintingHolder {
    static String getArtData(ItemStack stack) {
        return stack.getTag() == null ? null : stack.getTag().getCompound("PaintingData").getString(Art.ART_TAG);
    }

    static String getTypeData(ItemStack stack) {
        return stack.getTag() == null ? null : stack.getTag().getCompound("Painting").getString(Art.TYPE_TAG);
    }

    Art<?> getArt(ItemStack stack);

    Painting getType(ItemStack stack);
}
