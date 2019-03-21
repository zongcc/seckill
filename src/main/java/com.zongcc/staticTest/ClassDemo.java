package com.zongcc.staticTest;

/**
 * @author chunchengzong
 * @date 2018-07-30 11:41
 *
 **/
public class ClassDemo extends AbstractClassDemo{

    public ClassDemo(String name){
        super(name);
    }

    public void methodC(){
        //super.methodC();
        System.out.println("------>ClassDemo----methodC");
    }

    @Override
    public String methodD() {
        String imp = "methodD";
        return imp;
    }

    public static void main(String[] args) {
        AbstractClassDemo demo = new ClassDemo("name");
        demo.methodA();
        demo.methodB();
        demo.methodC();
        System.out.println(demo.methodD());
        staticMethod();
    }
}