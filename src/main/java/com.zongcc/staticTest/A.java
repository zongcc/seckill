package com.zongcc.staticTest;

import java.util.HashMap;

/**
 * Created by zongcc on 2017/3/1.
 */
class A {
    static {
        System.out.println("A的静态块");
    }
    private static String staticStr = getStaticStr();
    private String str = getStr();

    {
        System.out.println("A的实例块");
    }

    public A() {
        System.out.println("A的构造方法");
    }

    private static String getStaticStr() {
        System.out.println("A的静态属性初始化");
        return null;
    }
    private String getStr() {
        System.out.println("A的实例属性初始化");
        return null;
    }
    public static void main(String[] args) {
        new B();
        new B();
        //HashMap-hashset-stringbuffer-stringbuider-concurrenthashmap(get锁)-lock-sychorina-notify-volite-finally-int-integer
        //comparator-comparable-重构-重载-三范式-索引
    }

}
