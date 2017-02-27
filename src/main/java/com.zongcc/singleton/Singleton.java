package com.zongcc.singleton;

/**
 * Created by chunchengzong on 2016-12-06.
 */
public class Singleton {
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}

  /*  //判断是否侵入
    private static boolean flag = false;
    private Singleton(){
        synchronized(Singleton.class)
        {
            if(flag == false)
            {
                flag = !flag;
            }
            else
            {
                throw new RuntimeException("单例模式被侵犯！");
            }
        }
    }*/

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

