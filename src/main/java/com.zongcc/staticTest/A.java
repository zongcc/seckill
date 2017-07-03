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

        /*A的静态块
        A的静态属性初始化
        B的静态属性初始化
        B的静态块
        ----父的静态代码块和静态属性（先后顺序看程序），子类的静态快和静态属性-----
        A的实例属性初始化
        A的实例块
        A的构造方法
        B的实例块
        B的实例属性初始化
        B的构造方法
        ----父类属性和代码块（顺序看程序），然后是父类构造方法，最后是子类同样顺序-----
        A的实例属性初始化
        A的实例块
        A的构造方法
        B的实例块
        B的实例属性初始化
        B的构造方法
        a == b
        b.equals(a)*/

        //HashMap-hashset-stringbuffer-stringbuider-concurrenthashmap(get锁)-lock-synchronized-notify(唤醒优先)-volatile-finally-int-integer
        //comparator-comparable-重构-重载-三范式-索引


    }

}
