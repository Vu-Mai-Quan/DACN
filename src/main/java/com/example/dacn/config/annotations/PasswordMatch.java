package com.example.dacn.config.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchImlp.class)
@Documented
public @interface PasswordMatch {
    String message() default "Passwords do not match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String passwordField();
    String confirmPasswordField();

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        PasswordMatch[] value();
    }
}
