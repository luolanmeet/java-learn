package io.seata.common.loader;

import java.lang.annotation.*;

/**
 * The interface Load level.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LoadLevel {
    /**
     * Name string.
     *
     * @return the string
     */
    String name();

    /**
     * Order int.
     *
     * @return the int
     */
    int order() default 0;

    /**
     * Scope enum.
     * @return
     */
    Scope scope() default Scope.SINGLETON;
}
