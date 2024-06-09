package com.kumoe.conquest.core.asset.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String name();

    String template();

    boolean plural() default false;
}
