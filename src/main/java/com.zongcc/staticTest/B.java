package com.zongcc.staticTest;

/**
 * Created by zongcc on 2017/3/1.
 */
class B extends A{
    private static String staticStr = getStaticStr();
    static {
        System.out.println("B的静态块");
    }
    {
        System.out.println("B的实例块");
    }
    public B() {
        System.out.println("B的构造方法");
    }
    private String str = getStr();
    private static String getStaticStr() {
        System.out.println("B的静态属性初始化");
        return null;
    }
    private String getStr() {
        System.out.println("B的实例属性初始化");
        return null;
    }
}
