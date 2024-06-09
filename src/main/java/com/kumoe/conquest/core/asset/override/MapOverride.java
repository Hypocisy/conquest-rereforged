package com.kumoe.conquest.core.asset.override;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.kumoe.conquest.core.asset.template.JsonOverride;

import java.io.IOException;
import java.util.Map;

public class MapOverride implements JsonOverride {
    private static final JsonElement ANY_KEY = new JsonPrimitive("*");
    private final String key;
    private final JsonElement any;
    private final Map<JsonElement, JsonElement> overrides;

    public MapOverride(String key, Map<JsonElement, JsonElement> overrides) {
        this.key = key;
        this.overrides = overrides;
        this.any = (JsonElement) overrides.get(ANY_KEY);
    }

    public boolean apply(JsonElement input, JsonWriter writer) throws IOException {
        JsonElement output = (JsonElement) this.overrides.getOrDefault(input, this.any);
        if (output != null) {
            Streams.write(output, writer);
            return true;
        } else {
            return false;
        }
    }

    public boolean appliesTo(String key, JsonElement value) {
        return this.key.equals(key) && value.isJsonPrimitive();
    }
}
