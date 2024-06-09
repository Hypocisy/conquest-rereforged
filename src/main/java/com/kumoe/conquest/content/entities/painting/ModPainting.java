package com.kumoe.conquest.content.entities.painting;

import com.kumoe.conquest.api.painting.Painting;
import com.kumoe.conquest.api.util.Translateable;
import net.minecraft.resources.ResourceLocation;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ModPainting implements Translateable, Painting {
    private static final ResourceLocation ITEM = new ResourceLocation("conquest", "painting");
    private static final ModPainting UNKNOWN = new ModPainting("unknown");
    private static final Map<String, ModPainting> types = new ConcurrentHashMap();
    private final String name;
    private final ResourceLocation texture;

    private ModPainting(String name) {
        this.name = name;
        this.texture = new ResourceLocation("conquest", "textures/entity/painting/" + name + ".png");
    }

    public boolean isPresent() {
        return this != UNKNOWN;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getItemName() {
        return ITEM;
    }

    public ResourceLocation getRegistryName() {
        return this.texture;
    }

    public String getTranslationKey() {
        return this.getTranslationKey("conquest");
    }

    public String getTranslationKey(String parent) {
        return parent + "." + this.getName();
    }

    public static ModPainting fromId(String id) {
        return id == null ? UNKNOWN : (ModPainting)types.getOrDefault(id, UNKNOWN);
    }

    public static ModPainting fromName(String name) {
        ModPainting type = fromId(name);
        if (type != UNKNOWN) {
            return type;
        } else {
            System.out.println(name);
            Iterator var2 = types.values().iterator();

            ModPainting t;
            do {
                if (!var2.hasNext()) {
                    return UNKNOWN;
                }

                t = (ModPainting)var2.next();
                if (name.equalsIgnoreCase(t.getName())) {
                    return t;
                }
            } while(!name.equalsIgnoreCase(t.getDisplayName()));

            return t;
        }
    }

    public static ModPainting register(String id) {
        ModPainting painting = new ModPainting(id);
        types.put(id, painting);
        return painting;
    }

    public static Stream<String> getIds() {
        return types.values().stream().map(ModPainting::getName);
    }
}