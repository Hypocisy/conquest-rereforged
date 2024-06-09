package com.kumoe.conquest.api.painting.art;

import java.util.Iterator;
import java.util.List;

public interface Art<T> {
    String DATA_TAG = "Painting";
    String TYPE_TAG = "TypeID";
    String ART_TAG = "ArtID";

    int u();

    int v();

    int width();

    int height();

    int textureWidth();

    int textureHeight();

    T getReference();

    String getName();

    String getDisplayName();

    List<Art<T>> getAll();

    ArtRenderer getRenderer();
    static <T> Art find(T art, List<Art<T>> arts) {
        Iterator<Art<T>> artIterator = arts.iterator();

        Art a;
        do {
            if (!artIterator.hasNext()) {
                return null;
            }

            a = artIterator.next();
        } while(a.getReference() != art);

        return a;
    }
}
