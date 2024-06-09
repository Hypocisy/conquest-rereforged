package com.kumoe.conquest.content.entities;

import com.kumoe.conquest.content.entities.painting.EntityPainting;
import com.kumoe.conquest.content.entities.seat.SeatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityTypes {
    public static final EntityType<EntityPainting> PAINTING;
    public static final EntityType<SeatEntity> SEAT;

    public EntityTypes() {
    }

    public static void entityTypesInit() {
    }

    private static <T extends Entity> EntityType<T> build(String name, EntityType.Builder<T> builder) {
        EntityType<T> type = builder.build(name);
        return type;
    }

    static {
        PAINTING = build("conquest:painting", EntityType.Builder.of(EntityPainting::new, SpawnGroup.MISC).setDimensions(0.5F, 0.5F));
        SEAT = build("conquest:seat", Builder.create(SeatEntity::new, SpawnGroup.MISC).setDimensions(0.0F, 0.0F));
    }
}