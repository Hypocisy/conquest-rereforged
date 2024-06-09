package com.kumoe.conquest.core.asset.template;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonWriter;
import com.kumoe.conquest.core.util.ByteStream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class JsonTemplate {
    private static final JsonParser parser = new JsonParser();
    private static final Object lock = new Object();
    private final String path;
    private final ResourceLocation location;
    private JsonObject cached;

    JsonTemplate(String location) {
        String root = "";
        if (location.startsWith("assets/")) {
            root = "assets/";
            location = location.substring("assets/".length());
        } else if (location.startsWith("data/")) {
            root = "data/";
            location = location.substring("data/".length());
        }

        int i = location.indexOf(47);
        String domain = location.substring(0, i);
        String path = location.substring(i + 1);
        this.location = new ResourceLocation(domain, path);
        this.path = root + domain + "/" + path;
    }

    public String toString() {
        return "JsonTemplate{location=" + this.location + "}";
    }

    public JsonElement getJson(ResourceManager resourceManager, JsonOverride overrides) throws IOException {
        JsonTreeWriter writer = new JsonTreeWriter();
        this.apply(resourceManager, writer, overrides);
        return writer.get();
    }

    public void apply(ResourceManager resourceManager, JsonWriter writer, JsonOverride overrides) throws IOException {
        JsonObject object = this.getJson(resourceManager);
        this.write(writer, overrides, object);
    }

    public InputStream getInputStream(ResourceManager resourceManager, JsonOverride overrides) throws IOException {
        ByteStream.Output out = new ByteStream.Output();

        InputStream var11;
        try {
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out));

            try {
                this.apply(resourceManager, writer, overrides);
            } catch (Throwable var9) {
                try {
                    writer.close();
                } catch (Throwable var8) {
                    var9.addSuppressed(var8);
                }

                throw var9;
            }

            writer.close();
            var11 = out.toInputStream();
        } catch (Throwable var10) {
            try {
                out.close();
            } catch (Throwable var7) {
                var10.addSuppressed(var7);
            }

            throw var10;
        }

        out.close();
        return var11;
    }

    private JsonObject getJson(ResourceManager resourceManager) throws IOException {
        synchronized(lock) {
            if (this.cached == null) {
                try {
                    Resource resource = resourceManager.getResource(this.location).get();
                    InputStream in = resource.open();

                    try {
                        Reader reader = new InputStreamReader(in);

                        try {
                            JsonElement element = parser.parse(reader);
                            if (!element.isJsonObject()) {
                                throw new IOException("resource is not a template object");
                            }

                            this.cached = element.getAsJsonObject();
                        } catch (Throwable var11) {
                            try {
                                reader.close();
                            } catch (Throwable var10) {
                                var11.addSuppressed(var10);
                            }

                            throw var11;
                        }

                        reader.close();
                    } catch (Throwable var12) {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (Throwable var9) {
                                var12.addSuppressed(var9);
                            }
                        }

                        throw var12;
                    }

                    if (in != null) {
                        in.close();
                    }
                } catch (Throwable var13) {
                    Throwable t = var13;
                    throw new IOException(this.path, t);
                }
            }
        }

        return this.cached;
    }

    private void write(JsonWriter writer, JsonOverride overrides, JsonElement element) throws IOException {
        Iterator var4;
        if (element.isJsonArray()) {
            writer.beginArray();
            var4 = element.getAsJsonArray().iterator();

            while(var4.hasNext()) {
                JsonElement entry = (JsonElement)var4.next();
                this.write(writer, overrides, entry);
            }

            writer.endArray();
        } else if (!element.isJsonObject()) {
            Streams.write(element, writer);
        } else {
            writer.beginObject();
            var4 = element.getAsJsonObject().entrySet().iterator();

            while(var4.hasNext()) {
                Map.Entry entry = (Map.Entry)var4.next();
                String key = (String)entry.getKey();
                JsonElement value = (JsonElement)entry.getValue();
                writer.name(key);
                boolean overwritten = overrides.appliesTo(key, value) && overrides.apply(value, writer);
                if (!overwritten) {
                    this.write(writer, overrides, value);
                }
            }

            writer.endObject();
        }
    }
}