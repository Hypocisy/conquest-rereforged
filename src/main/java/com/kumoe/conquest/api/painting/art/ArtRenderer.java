package com.kumoe.conquest.api.painting.art;

import com.kumoe.conquest.api.painting.Painting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public enum ArtRenderer {
    MOD {
        public void render(Painting painting, Art<?> art, GuiGraphics drawContext, int x, int y, int w, int h) {
            RenderSystem.setShaderTexture(0, painting.getRegistryName());
            drawContext.blit(painting.getRegistryName(), x, y, w, h, art.u(), art.v(), art.width(), art.height(), art.textureWidth(), art.textureHeight());

        }
    }, VANILLA {
        public void render(Painting painting, Art<?> art, GuiGraphics drawContext, int x, int y, int w, int h) {
            String artName = art.getName().replace("minecraft:", "");
            RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft:texture/painting/" + artName + ".png"));
            drawContext.blit(painting.getRegistryName(), x, y, w, h, art.u(), art.v(), art.width(), art.height(), art.textureWidth(), art.textureHeight());
        }
    };

    ArtRenderer() {
    }

    public abstract void render(Painting painting, Art<?> art, GuiGraphics drawContext, int x, int y, int w, int h);

}
