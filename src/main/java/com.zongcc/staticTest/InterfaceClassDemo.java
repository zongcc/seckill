package com.zongcc.staticTest;

/**
 * @author chunchengzong
 * @date 2018-07-30 14:43
 **/
public class InterfaceClassDemo implements InterfaceDemo{

    @Override
    public void methodA() {
        System.out.println("------>methodA");
    }

    @Override
    public void methodB() {
        System.out.println("------>methodB");
    }

    public static void main(String[] args) {
        InterfaceDemo demo = new InterfaceClassDemo();
        demo.methodA();
        demo.methodB();
    }
}
