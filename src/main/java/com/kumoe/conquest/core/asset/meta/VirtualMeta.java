package com.kumoe.conquest.core.asset.meta;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.kumoe.conquest.core.asset.VirtualResource;
import com.kumoe.conquest.core.util.ByteStream;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class VirtualMeta implements VirtualResource {
    public static final int PACK_FORMAT = 5;
    private final String description;
    private final String namespace;

    public VirtualMeta(String namespace, String description) {
        this.description = description;
        this.namespace = namespace;
    }

    public PackMetadataSection toMetadata() {
        return PackMetadataSection.TYPE.fromJson(this.toJson());
    }

    public String getPath() {
        return "pack.mcmeta";
    }

    public String getNamespace() {
        return this.namespace;
    }

    public PackType getType() {
        return PackType.CLIENT_RESOURCES;
    }

    public JsonElement getJson(ResourceManager resourceManager) throws IOException {
        return this.toJson();
    }

    public InputStream getInputStream(ResourceManager resourceManager) throws IOException {
        ByteStream.Output out = new ByteStream.Output();
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out));

        try {
            Streams.write(this.toJson(), writer);
        } catch (Throwable var7) {
            try {
                writer.close();
            } catch (Throwable var6) {
                var7.addSuppressed(var6);
            }

            throw var7;
        }

        writer.close();
        return out.toInputStream();
    }

    private JsonObject toJson() {
        JsonObject pack = new JsonObject();
        pack.addProperty("description", this.description);
        pack.addProperty("pack_format", 5);
        JsonObject meta = new JsonObject();
        meta.add("pack", pack);
        meta.add("language", new JsonObject());
        return pack;
    }
}
