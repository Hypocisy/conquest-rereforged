package com.kumoe.conquest.core.asset.override;


import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import com.kumoe.conquest.core.asset.template.JsonOverride;

import java.io.IOException;

public class SingleOverride implements JsonOverride {
    private final boolean any;
    private final String key;
    private final JsonElement find;
    private final JsonElement replace;

    public SingleOverride(String key, JsonElement replace) {
        this(key, new JsonPrimitive("*"), replace);
    }

    public SingleOverride(String key, JsonElement find, JsonElement replace) {
        this.key = key;
        this.find = find;
        this.replace = replace;
        this.any = find.getAsString().equals("*");
    }

    public boolean apply(JsonElement input, JsonWriter writer) throws IOException {
        if (!this.any && !this.find.equals(input)) {
            return false;
        } else {
            Streams.write(this.replace, writer);
            return true;
        }
    }

    public boolean appliesTo(String key, JsonElement value) {
        return this.key.equals(key) && value.isJsonPrimitive();
    }
}
