package com.kumoe.conquest.core.asset.template;

import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.kumoe.conquest.core.asset.override.EmptyOverride;

import java.io.IOException;

public interface JsonOverride {
    boolean apply(JsonElement var1, JsonWriter var2) throws IOException;

    boolean appliesTo(String var1, JsonElement var2);

    default JsonOverride add(final JsonOverride other) {
        if (this == EmptyOverride.EMPTY) {
            return other;
        } else if (other == EmptyOverride.EMPTY) {
            return this;
        } else {
            final JsonOverride self = this;
            return new JsonOverride() {
                private final JsonOverride a = self;
                private final JsonOverride b = other;

                public boolean apply(JsonElement input, JsonWriter output) throws IOException {
                    return this.a.apply(input, output) || this.b.apply(input, output);
                }

                public boolean appliesTo(String key, JsonElement value) {
                    return this.a.appliesTo(key, value) || this.b.appliesTo(key, value);
                }
            };
        }
    }
}
