package com.internship.service.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeDatabase {
    String value() default "";
}
