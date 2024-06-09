package com.kumoe.conquest.content.entities.painting.art;

import com.kumoe.conquest.api.painting.art.Art;
import com.kumoe.conquest.api.painting.art.ArtRenderer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModArt implements Art<ArtType> {
    public static final List<Art<ArtType>> ALL = Collections.unmodifiableList((List) Stream.of(ArtType.values()).map(ModArt::new).collect(Collectors.toList()));
    private final ArtType art;

    public ModArt(ArtType art) {
        this.art = art;
    }

    public int u() {
        return this.art.offsetX;
    }

    public int v() {
        return this.art.offsetY;
    }

    public int width() {
        return this.art.sizeX;
    }

    public int height() {
        return this.art.sizeY;
    }

    public int textureWidth() {
        return 256;
    }

    public int textureHeight() {
        return 256;
    }

    public ArtType getReference() {
        return this.art;
    }

    public String getName() {
        return this.art.shapeId;
    }

    @Override
    public String getDisplayName() {
        return this.art.getDisplayName();
    }

    public String getDisplayName(String parent) {
        return this.art.getDisplayName(parent);
    }

    public List<Art<ArtType>> getAll() {
        return ALL;
    }

    public ArtRenderer getRenderer() {
        return ArtRenderer.MOD;
    }

    public static Art<ArtType> of(ArtType art) {
        return Art.find(art, ALL);
    }

    public static Art<ArtType> fromName(String name) {
        return Art.find(ArtType.fromName(name), ALL);
    }
}