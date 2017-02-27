package com.zongcc.cache.annotation;

import java.lang.annotation.*;

/**
 * Cache Remove Annotation
 * 
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheRemove {
    
    /**
     * Delete mutikey
     * 
     * @return
     */
    String[] keys();
    
}
