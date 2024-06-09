package com.kumoe.conquest.content.entities.init;

import com.kumoe.conquest.content.entities.EntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EntityClientInit {
    public EntityClientInit() {
    }

    public static void setup() {
        EntityRendererRegistry.register(EntityTypes.PAINTING, PaintingRenderer::new);
        EntityRendererRegistry.register(EntityTypes.SEAT, SeatRenderer::new);
    }
}
