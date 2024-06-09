package com.kumoe.conquest.core.asset.override;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.kumoe.conquest.core.asset.template.JsonOverride;

import java.io.IOException;

public class EmptyOverride implements JsonOverride {
    public static final EmptyOverride EMPTY = new EmptyOverride();

    private EmptyOverride() {
    }

    public boolean apply(JsonElement input, JsonWriter output) throws IOException {
        return false;
    }

    public boolean appliesTo(String key, JsonElement value) {
        return false;
    }
}
