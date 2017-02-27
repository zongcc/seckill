/**
 * Copyright (c) 2010-2012 All Rights Reserved.
 */
package com.zongcc.cache.memcache;

import java.util.Map;

/**
 * Cache Interface
 * 
 */
public interface CacheClient {
    
    /**
     * Get value
     * 
     * @param key
     * @return
     */
    public <T> T get(String key);
    
    /**
     * Get multivalue
     * 
     * @param keys
     * @return
     */
    public <T> Map<String, Object> getMulti(String[] keys);
    
    /**
     * Store key value
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value);
    
    /**
     * 
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public boolean set(String key, Object value, int seconds);
    
    /**
     * Delete key value
     * 
     * @param key
     * @return
     */
    public boolean delete(String key);
    
    /**
     * Increase counter
     * 
     * @param key
     * @param value
     * @return
     */
    public long incr(String key, long value);
    
    /**
     * Decrease counter
     * 
     * @param key
     * @param value
     * @return
     */
    public long decr(String key, long value);
    
    /**
     * Get counter
     * 
     * @param key
     * @return
     */
    public long getCounter(String key);
    
    /**
     * Store counter
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean storeCounter(String key, long value);
    
    /**
     * Flash cache
     */
    public void flush();
    
    
}
