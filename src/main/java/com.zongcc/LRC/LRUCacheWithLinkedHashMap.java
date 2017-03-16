package com.zongcc.LRC;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by chunchengzong on 2017-03-16.
 */
public class LRUCacheWithLinkedHashMap {
    private int capacity;
    private Map<Integer, Integer> cache;

    public LRUCacheWithLinkedHashMap(final int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            // 定义put后的移除规则，大于容量就删除eldest
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else
            return -1;
    }

    public void set(int key, int value) {
        cache.put(key, value);
    }
}