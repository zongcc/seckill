package com.zongcc.singleton;

/**
 * 枚举单例模式：防止侵入
 * Created by chunchengzong on 2016-12-06.
 */
public enum  EasySingleton {
    INSTANCE;
     private Singleton name;
     EasySingleton(){
        name = Singleton.getInstance();
    }
    public Singleton getInstance(){
        return name;
    }
}
