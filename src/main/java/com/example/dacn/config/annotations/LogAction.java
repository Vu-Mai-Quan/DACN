package com.example.dacn.config.annotations;

import com.example.dacn.enumvalues.EnumBehavior;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAction {
    String descriptions() default "";

    EnumBehavior action();

    String bangTuongTac() default "";
}
