/**
 * Copyright (c) 2010-2012 All Rights Reserved.
 */
package com.zongcc.cache.memcache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache constant
 * 
 */
public class CacheConstant {
    
    /** Cache key separator */
    public static final String CACHE_KEY_SEPARATOR = ":";
    
    /** To store method args name */
    public static final ConcurrentHashMap<String, String[]> METHOD_ARGS_NAME = new ConcurrentHashMap<String, String[]>();
    
    
}
