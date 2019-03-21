package com.zongcc.staticTest;

/**
 * @author chunchengzong
 * @date 2018-07-30 11:37
 **/
public abstract class AbstractClassDemo {

    private String name;

    public AbstractClassDemo(String name){
        this.name = name;
    }

    public static void methodA() {
        System.out.println("------>methodA");
    }

    public void methodB(){
        System.out.println("------>methodB");
    }

    public void methodC(){
        System.out.println("------>methodC--->"+ name);
    }

    public abstract String methodD();

    public static void staticMethod(){
        System.out.println("------>staticMethod");
    }
}