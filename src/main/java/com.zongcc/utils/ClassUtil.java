package com.zongcc.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chunchengzong on 2017-02-22.
 */
public class ClassUtil {
    /**
     * 获取类方法
     * @param obj
     */
    public static void getMethodClass(Object obj){
        //要获取类的信息》》首先我们要获取类的类类型
        Class c = obj.getClass();
        //我们知道Object类是一切类的父类，所以我们传递的是哪个子类的对象，c就是该子类的类类型。
        //接下来我们要获取类的名称
        System.out.println("类的名称是:"+c.getName());
        //如果我们要获得所有的方法，可以用getMethods()方法，这个方法获取的是所有的Public的函数，
        // 包括父类继承而来的。如果我们要获取所有该类自己声明的方法，就可以用getDeclaredMethods()方法，这个方法是不问访问权限的。
        Method[] methods = c.getDeclaredMethods();
        for (Method method:methods){
            Class<?> returnType = method.getReturnType();
            System.out.print(returnType.getName() +" ");
            //得到方法的名称
            System.out.print(method.getName()+"(");
            Class[] parameterTypes = method.getParameterTypes();
           for(Class cl:parameterTypes){
               System.out.print(cl.getName()+", ");
           }
            System.out.println(")");
        }
    }

    /**
     * 获取
     * @param object
     */
    public static void getFieldClass(Object object){
        Class c = object.getClass();
        //getFields()方法获取的所有的public的成员变量的信息。和方法的反射那里public的成员变量，也有一个获取所有自己声明的成员变量的信息
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            //得到成员变量的类型的类类型
            Class fieldType = field.getType();
            String typeName = fieldType.getName();
            //得到成员变量的名称
            String fieldName = field.getName();
            System.out.println(typeName+" "+fieldName);
        }
    }

    /**
     * 获取构造函数信息
     * @param obj
     */
    public static void printConMessage(Object obj){
        Class c = obj.getClass();
        /*
        * 首先构造函数也是对象，是java.lang.Constructor类的对象
        * 也就是java.lang. Constructor中封装了构造函数的信息
        * 和前面说到的一样，它也有两个方法：
        * getConstructors()方法获取所有的public的构造函数
        * getDeclaredConstructors()方法得到所有的自己声明的构造函数
        */
        //Constructor[] cs = c.getConstructors();
        Constructor[] cs = c.getDeclaredConstructors();
        for (Constructor constructor : cs) {
            //我们知道构造方法是没有返回值类型的，但是我们可以：
            System.out.print(constructor.getName()+"(");
            //获取构造函数的参数列表》》得到的是参数列表的类类型
            Class[] paramTypes = constructor.getParameterTypes();
            for (Class class1 : paramTypes) {
                System.out.print(class1.getName()+",");
            }
            System.out.println(")");
        }
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClassUtil c = new ClassUtil();
        c.getMethodClass(new Object());
        System.out.println("=================");
        c.getFieldClass("hello");
        System.out.println("=================");
        c.printConMessage("hello");
        System.out.println("=================");
        Class cl = c.getClass();
        Method m = cl.getMethod("getMethodClass",new Class[]{Object.class});
        m.invoke(c,new Object[]{new Object()});
    }
}
