package com.kumoe.conquest.core.asset.lang;

import com.google.gson.JsonElement;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonWriter;
import com.kumoe.conquest.core.asset.VirtualResource;
import com.kumoe.conquest.core.util.ByteStream;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;


public class VirtualLang implements VirtualResource {
    private final String path;
    private final String namespace;

    public VirtualLang(String namespace) {
        this.namespace = namespace;
        this.path = "assets/" + namespace + "/lang/en_us.json";
    }

    public String getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public PackType getType() {
        return PackType.CLIENT_RESOURCES;
    }

    public JsonElement getJson(ResourceManager resourceManager) throws IOException {
        JsonTreeWriter writer = new JsonTreeWriter();
        this.write(writer);
        return writer.get();
    }

    public InputStream getInputStream(ResourceManager resourceManager) throws IOException {
        ByteStream.Output output = new ByteStream.Output();
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(output));
        this.write(writer);
        writer.flush();
        return output.toInputStream();
    }

    private void write(JsonWriter writer) throws IOException {
        writer.setIndent("  ");
        writer.beginObject();
        Set<String> visited = new HashSet<>();
        Translations.getInstance().forEach((key, value) -> {
            this.writeSafe(key, value, writer, visited);
        });
        this.writeTranslations((DefaultedRegistry<?>) Registries.BLOCK, "block", writer, visited);
        this.writeTranslations((DefaultedRegistry<?>) Registries.ITEM, "item", writer, visited);
        this.writeTranslations((DefaultedRegistry<?>) Registries.ENTITY_TYPE, "entity", writer, visited);
        writer.endObject();
    }

    private void writeSafe(String key, String value, JsonWriter writer, Set<String> visited) {
        if (visited.add(key)) {
            try {
                writer.name(key).value(value);
            } catch (IOException var6) {
            }
        }

    }

    private void writeTranslations(DefaultedRegistry<?> registry, String type, JsonWriter writer, Set<String> visited) throws IOException {

        for (Object o : registry) {
            ResourceLocation name = (ResourceLocation) o;
            if (name != null && name.getNamespace().equals(this.getNamespace())) {
                String key = Translations.getKey(type, name);
                if (visited.add(key)) {
                    String value = Translations.translate(name.getPath());
                    writer.name(key);
                    writer.value(value);
                }
            }
        }

    }
}
