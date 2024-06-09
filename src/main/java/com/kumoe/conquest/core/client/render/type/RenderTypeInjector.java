package com.kumoe.conquest.core.client.render.type;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class RenderTypeInjector implements MultiBufferSource {
    protected final MultiBufferSource delegate;

    public RenderTypeInjector(MultiBufferSource delegate) {
        this.delegate = delegate;
    }

    public @NotNull VertexConsumer getBuffer(@NotNull RenderType type) {
        return this.delegate.getBuffer(this.getRenderType(type));
    }

    protected RenderType getRenderType(RenderType type) {
        return type;
    }
}
