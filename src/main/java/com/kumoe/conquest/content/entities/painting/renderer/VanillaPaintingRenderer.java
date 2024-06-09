package com.kumoe.conquest.content.entities.painting.renderer;

import com.kumoe.conquest.content.entities.painting.EntityPainting;
import com.kumoe.conquest.core.client.render.type.RenderTypeInjector;
import com.kumoe.conquest.core.client.render.type.ReplaceFirstInjector;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.decoration.Painting;

public class VanillaPaintingRenderer extends PaintingRenderer {
    public VanillaPaintingRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }

    public void render(EntityPainting entity, float yaw, float ticks, PoseStack stack, MultiBufferSource buffer, int light) {
        RenderTypeInjector injector = new ReplaceFirstInjector(buffer, RenderType.entityCutout(this.getTexture(entity)));
        super.render(entity, yaw, ticks, stack, injector, light);
    }
}
