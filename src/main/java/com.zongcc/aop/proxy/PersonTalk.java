package com.zongcc.aop.proxy;

/**
 * Created by zongcc on 2016/12/26.
 */
public class PersonTalk implements ITalk {
    private String name;
    public PersonTalk(){}
    public PersonTalk(String name){
        this.name = name;
    }
    @Override
    public void talk(String message) {
        System.out.println("persontalk name is "+ name+" message is "+message);
    }
}
