package com.zongcc.aop.proxy;

import com.zongcc.aop.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zongcc on 2016/12/26.
 */
public class MainProxy {

    public static void main(String[] args){
       /* //普通
        PersonTalk personTalk = new PersonTalk("zcc");
        personTalk.talk("person");
        //静态代理
        ProxyTalk proxyTalk = new ProxyTalk(personTalk);
        proxyTalk.talk("静态代理");
        //JDK动态代理
        ITalk iTalk = (ITalk) new DynamicProxy().bind(personTalk);
        iTalk.talk("动态代理");
        //cglib代理
        PersonTalk peopleTalk = (PersonTalk) new CglibProxy().getInstance(new PersonTalk());
        peopleTalk.talk("cglib代理");*/

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-aop.xml");
        TestService target = (TestService) applicationContext.getBean("testService");
        target.helloWorld();
        System.out.println("------无敌分割线-----");
        target.helloWorld();
    }
}
