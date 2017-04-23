package com.zongcc.staticTest;

import com.zongcc.utils.JsonMapperUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public A(int a) {
        System.out.println("A的构造方法有变量的");
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
        int a = 25;
        Integer b = 25;
        if(a == b){
            System.out.println("a == b");
        }
        if (b.equals(a)){
            System.out.println("b.equals(a)");
        }
        //HashMap-hashset-stringbuffer-stringbuider-concurrenthashmap(get锁)-lock-synchronized-notify(唤醒优先)-volatile-finally-int-integer
        //comparator-comparable-重构-重载-三范式-索引


    }

}
