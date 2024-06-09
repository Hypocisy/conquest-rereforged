package com.kumoe.conquest.core.asset.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
    String name();

    String parent() default "";

    String template();

    boolean plural() default false;
}
