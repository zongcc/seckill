package com.zongcc.aop;



import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by chunchengzong on 2016-09-28.
 */
public class BeforeAdvice implements MethodBeforeAdvice {
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("===========before advice");
    }
}
