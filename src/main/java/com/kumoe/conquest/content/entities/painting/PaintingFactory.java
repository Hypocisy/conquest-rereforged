package com.kumoe.conquest.content.entities.painting;

import com.kumoe.conquest.api.painting.art.Art;
import com.kumoe.conquest.content.entities.painting.art.ArtType;
import com.kumoe.conquest.content.entities.painting.art.ModArt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.logging.Level;

public interface PaintingFactory<T extends HangingEntity> {
    PaintingFactory<EntityPainting> MOD = (world, pos, side, typeName, artName) -> {
        ModPainting type = ModPainting.fromName(typeName);
        Art<ArtType> art = ModArt.fromName(artName);
        return type != null && art != null ? new EntityPainting(world, pos, side, type, (ArtType)art.getReference()) : null;
    };
    PaintingFactory<PaintingEntity> VANILLA = (world, pos, side, typeName, artName) -> {
        Art<PaintingVariant> art = VanillaArt.fromName(artName);
        if (art == null) {
            return new PaintingEntity(world, pos, side, Registries.PAINTING_VARIANT.createEntry((PaintingVariant)art.getReference()));
        } else {
            PaintingEntity painting = new PaintingEntity(world, pos, side, (RegistryEntry) Registries.PAINTING_VARIANT.getEntry(Registries.PAINTING_VARIANT.getRawId((PaintingVariant)art.getReference())).get());
            painting.setPosition((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
            return painting;
        }
    };

    T create(Level world, BlockPos pos, Direction side, String typeName, String artName);
}
