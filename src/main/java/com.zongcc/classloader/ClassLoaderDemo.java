package com.zongcc.classloader;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * http://www.importnew.com/24036.html
 * 注释TestService.java
 * Created by chunchengzong on 2017-03-31.
 */
public class ClassLoaderDemo {
    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }
    }

    ;

    public static void main(String args[]) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("D:\\");
        Class clazz = classLoader.loadClass("com.zongcc.aop.TestService");
        Object obj = clazz.newInstance();
        Method helloMethod = clazz.getDeclaredMethod("helloWorld", null);
        helloMethod.invoke(obj, null);
    }
}
