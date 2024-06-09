package com.kumoe.conquest.core.asset.template;

import com.google.common.io.ByteSource;
import com.kumoe.conquest.core.asset.VirtualResource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

public class ByteResource extends ByteSource {
    private final ResourceManager resourceManager;
    private final VirtualResource resource;

    public ByteResource(VirtualResource resource, ResourceManager manager) {
        this.resourceManager = manager;
        this.resource = resource;
    }

    public @NotNull InputStream openStream() throws IOException {
        return this.resource.getInputStream(this.resourceManager);
    }
}