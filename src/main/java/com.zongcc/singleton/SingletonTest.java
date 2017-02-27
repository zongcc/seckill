package com.zongcc.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chunchengzong on 2016-12-06.
 */
public class SingletonTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(Singleton.getInstance());
        Singleton a =  EasySingleton.INSTANCE.getInstance();
        Singleton b =  EasySingleton.INSTANCE.getInstance();
        System.out.println(a == b);

        Class<?> classType = Singleton.class;
        Constructor<?> c = classType.getDeclaredConstructor(null);
        c.setAccessible(true);
        Singleton e1 = (Singleton)c.newInstance();
        Singleton e2 = Singleton.getInstance();
        System.out.println(e1==e2);

        Map map = new HashMap<String,Object>();

    }
}
