package com.zongcc.LRU;

/**
 * Created by chunchengzong on 2017-03-16.
 */
public class Main {
    public static void main(String[] args){
        LRUCacheWithLinkedHashMap lruCache = new LRUCacheWithLinkedHashMap(5);
        lruCache.set(0,10);
        lruCache.set(1,11);
        lruCache.set(2,12);
        lruCache.set(3,13);
        lruCache.set(4,14);
        lruCache.set(5,15);
        for (int i=0;i<7;i++){
            System.out.println(lruCache.get(i));
        }
        System.out.println("===============================================");
        LRUCache cache = new LRUCache(5);
        cache.set(0,10);
        cache.set(1,11);
        cache.set(2,12);
        cache.set(3,13);
        cache.set(4,14);
        cache.set(5,15);
        for (int i=0;i<7;i++){
            System.out.println(cache.get(i));
        }
        System.out.println("===============================================");
        LRU lru = new LRU(5);
        lru.set("0","10");
        lru.set("1","11");
        lru.set("2","12");
        lru.set("3","13");
        lru.set("4","14");
        lru.set("5","15");
        for (int i=0;i<7;i++){
            System.out.println(lru.get(i+""));
        }
    }
}
