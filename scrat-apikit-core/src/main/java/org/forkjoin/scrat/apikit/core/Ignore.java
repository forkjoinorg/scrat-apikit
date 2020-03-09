package org.forkjoin.scrat.apikit.core;

import java.lang.annotation.*;

/**
 * @author zuoge85@gmail.com on 2017/11/7.
 */
@Target({ElementType.PARAMETER,ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface Ignore {
}
