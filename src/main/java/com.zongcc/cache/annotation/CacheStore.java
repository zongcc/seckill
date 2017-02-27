package com.zongcc.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Method Cache Annotation
 * 
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheStore {
    
    /**
     * Cache Key
     * 
     * @return
     */
    String key() default "";
    
    /**
     * Cache Expire Value
     * 
     * @return
     */
    int expireValue() default -1;
    
    /**
     * Expire Measure Default is minutes
     * 
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;
    
}
