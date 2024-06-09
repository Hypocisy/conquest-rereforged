package com.kumoe.conquest.core.asset.template;

import com.kumoe.conquest.core.util.cache.Cache;

public class TemplateCache extends Cache<String, JsonTemplate> {
    private static final TemplateCache instance = new TemplateCache();

    private TemplateCache() {
    }

    public static TemplateCache getInstance() {
        return instance;
    }

    public JsonTemplate compute(String location) {
        return new JsonTemplate(location);
    }
}
