package com.kumoe.conquest.core.asset.annotation;


import com.kumoe.conquest.core.util.RenderLayer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Render {
    RenderLayer value();
}
