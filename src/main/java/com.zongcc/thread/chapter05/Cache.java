package com.zongcc.thread.chapter05;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 10-16
 */
public class Cache {
    private static final Map<String, Object>    map = new HashMap<String, Object>();
    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static final Lock                   r   = rwl.readLock();
    private static final Lock                   w   = rwl.writeLock();

    public static final Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    public static final Object put(String key, Object value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public static final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }

    public static void main(String[] args) {
        Cache.put("1","1");
        System.out.println(Cache.get("1"));
        Cache.put("2","2");
        System.out.println(Cache.get("3"));
        Cache.put("3","3");
        Cache.put("4","4");
        Cache.put("5","5");
        Cache.put("6","6");
        Cache.put("7","7");
        Cache.put("8","8");
        System.out.println(Cache.get("3"));
        Cache.clear();


    }
}
