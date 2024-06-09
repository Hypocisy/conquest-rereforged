//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kumoe.conquest.mixin;

import java.util.Map;

import com.kumoe.conquest.content.entities.painting.renderer.VanillaPaintingRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderers.class})
public abstract class EntityRenderersMixin {
    @Shadow
    @Final
    private static Map<EntityType<?>, EntityRendererProvider<?>> PROVIDERS;

    public EntityRenderersMixin() {
    }

    @Inject(
        method = {"<clinit>"},
        at = {@At("TAIL")}
    )
    private static void injected(CallbackInfo ci) {
        register(VanillaPaintingRenderer::new);
    }

    @Unique
    private static <T extends Entity> void register(EntityRendererProvider<T> aNew) {
        PROVIDERS.put(EntityType.PAINTING, aNew);
    }
}
