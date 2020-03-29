package com.zrs.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhang
 * @date 2020/3/27 0027
 * @time 0:27
 * @describe TODO
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Route {
  
    String path();

    String group() default "";

    String name() default "";


    int extras() default Integer.MIN_VALUE;

    int priority() default -1;
}
