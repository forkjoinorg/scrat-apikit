package org.forkjoin.scrat.apikit.core;

import java.lang.annotation.*;

/**
 * @author zuoge85 on 15/4/25.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Account {
    boolean value() default true;
}
