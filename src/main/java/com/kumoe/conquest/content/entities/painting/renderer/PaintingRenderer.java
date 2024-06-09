package com.kumoe.conquest.content.entities.painting.renderer;

import com.kumoe.conquest.content.entities.painting.EntityPainting;
import com.kumoe.conquest.content.entities.painting.art.ArtType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PaintingRenderer extends EntityRenderer<EntityPainting> {
    public PaintingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    public void render(EntityPainting painting, float yaw, float ticks, PoseStack matrix, MultiBufferSource buffer, int light) {
        matrix.pushPose();
        matrix.mulPose((new Quaternionf()).fromAxisAngleDeg(new Vector3f(0.0F, 1.0F, 0.0F), 180.0F - yaw));
        matrix.scale(0.0625F, 0.0625F, 0.0625F);
        ArtType art = painting.getArt();
        ResourceLocation texture = this.getTexture(painting);
        RenderType renderType = RenderType.entityCutout(texture);
        VertexConsumer builder = buffer.getBuffer(renderType);
        this.render(matrix, builder, painting, art.sizeX, art.sizeY, art.offsetX, art.offsetY);
        matrix.popPose();
        super.render(painting, yaw, ticks, matrix, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPainting entityPainting) {
        return this.getTexture(entityPainting);
    }

    public ResourceLocation getTexture(EntityPainting entity) {
        return entity.getPaintingType().getRegistryName();
    }

    private void render(PoseStack matrix, VertexConsumer builder, EntityPainting entity, int width, int height, int textureU, int textureV) {
        PoseStack.Pose entry = matrix.last();
        Matrix4f position = entry.pose();
        Matrix3f normals = entry.normal();
        float xCenter = (float)(-width) / 2.0F;
        float yCenter = (float)(-height) / 2.0F;

        for(int x = 0; x < width / 16; ++x) {
            for(int y = 0; y < height / 16; ++y) {
                float minX = xCenter + (float)(x * 16);
                float minY = yCenter + (float)(y * 16);
                float maxX = xCenter + (float)((x + 1) * 16);
                float maxY = yCenter + (float)((y + 1) * 16);
                int lightX = Mth.floor(entity.getX());
                int lightY = Mth.floor(entity.getY() + (double)((maxY + minY) / 2.0F / 16.0F));
                int lightZ = Mth.floor(entity.getZ());
                int light = LevelRenderer.getLightColor(entity.level(), new BlockPos(lightX, lightY, lightZ));
                float txMin = (float)(textureU + width - x * 16) / 256.0F;
                float txMax = (float)(textureU + width - (x + 1) * 16) / 256.0F;
                float tyMin = (float)(textureV + height - y * 16) / 256.0F;
                float tyMax = (float)(textureV + height - (y + 1) * 16) / 256.0F;
                this.vertex(position, normals, builder, maxX, minY, txMax, tyMin, 0.2F, 0, 0, -1, light);
                this.vertex(position, normals, builder, minX, minY, txMin, tyMin, 0.2F, 0, 0, -1, light);
                this.vertex(position, normals, builder, minX, maxY, txMin, tyMax, 0.2F, 0, 0, -1, light);
                this.vertex(position, normals, builder, maxX, maxY, txMax, tyMax, 0.2F, 0, 0, -1, light);
                this.vertex(position, normals, builder, maxX, maxY, txMax, tyMax, 0.2F, 0, 0, -1, light);
                this.vertex(position, normals, builder, minX, maxY, txMin, tyMax, 0.2F, 0, 0, -1, light);
                this.vertex(position, normals, builder, minX, minY, txMin, tyMin, 0.2F, 0, 0, -1, light);
                this.vertex(position, normals, builder, maxX, minY, txMax, tyMin, 0.2F, 0, 0, -1, light);
            }
        }

    }

    private void vertex(Matrix4f position, Matrix3f normals, VertexConsumer builder, float x, float y, float u, float v, float z, int nx, int ny, int nz, int light) {
        builder.vertex(position, x, y, z).color(255, 255, 255, 255).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normals, (float)nx, (float)ny, (float)nz).endVertex();
    }
}