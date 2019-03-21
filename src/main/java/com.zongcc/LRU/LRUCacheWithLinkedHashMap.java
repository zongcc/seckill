package com.zongcc.LRU;

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
        //如果 accessOrder 为 true，则会将 该节点移到链表尾部。也就是说指定为 LRU 顺序之后，在每次访问一个节点时，会将这个节点移到链表尾部，
        // 保证链表尾部是最近访问的节点，那么链表首部就是最近最久未使用的节点。
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

//class LRUCache<K, V> extends LinkedHashMap<K, V> {
//    private static final int MAX_ENTRIES = 3;
//
//    protected boolean removeEldestEntry(Map.Entry eldest) {
//        return size() > MAX_ENTRIES;
//    }
//
//    LRUCache() {
//        super(MAX_ENTRIES, 0.75f, true);
//    }
//}