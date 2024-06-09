package com.kumoe.conquest.content.entities.init;

import com.kumoe.conquest.content.entities.painting.ModPainting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;

import java.util.stream.IntStream;

public class EntityCommonInit {
    public EntityCommonInit() {
    }

    public static void entities() {
        registerPaintings();
    }

    private static void registerPaintings() {
        IntStream.range(0, 10).forEach((i) -> {
            ModPainting.register("painting" + i);
        });
        EntityTypes.entityTypesInit();
        Registry.register(Registries.ENTITY_TYPE, "conquest:painting", EntityTypes.PAINTING);
        Registry.register(Registries.ENTITY_TYPE, "conquest:seat", EntityTypes.SEAT);
    }
}