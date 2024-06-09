package com.kumoe.conquest.core.asset.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Ingredient {
    String name();

    String template();

    boolean plural() default false;
}
