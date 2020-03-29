package com.zrs.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhang
 * @date 2020/3/27 0027
 * @time 17:38
 * @describe TODO
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Autowired {
    String name() default "";


    boolean required() default false;

    String desc() default "";

}
