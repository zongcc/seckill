package com.zongcc.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zongcc on 2016/12/26.
 */
public class DynamicProxy implements InvocationHandler {
    /** 需要代理的目标类 */
    private Object target;

    /**
     * 写法固定，aop专用:绑定委托对象并返回一个代理类
     *
     * @param target
     * @return
     */
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * @param proxy
     *            target：指被代理的对象。
     * @param method
     *            method：要调用的方法
     * @param args
     *            [] args：方法调用时所需要的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        // 切面之前执行
        System.out.println("切面之前执行");
        // 执行业务
        result = method.invoke(target, args);
        // 切面之后执行
        System.out.println("切面之后执行");
        return result;
    }

}
