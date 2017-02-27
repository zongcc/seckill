package com.zongcc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.weaver.AjAttribute;

/**
 * Created by chunchengzong on 2016-09-27.
 */
public class AopAspect {
   /* public void beforeAdvice(String param) {
        System.out.println("===========before advice param:" + param);
    }*/
    public void beforeAdvice() {
        System.out.println("======================beforeAdvice advice");
    }
    //后置最终通知
  /*  public void afterFinallyAdvice() {
        System.out.println("======================afterFinallyAdvice advice");
    }
    public void afterAdvice() {
        System.out.println("======================afterAdvice advice");
    }
    public void afterReturningAdvice() {
        System.out.println("======================afterReturningAdvice advice");
    }
    public void afterThrowingAdvice() {
        System.out.println("======================afterThrowingAdvice advice");
    }

    public void afterReturningAdvice(Object retVal) {
        System.out.println("===========after returning advice retVal:" + retVal);
    }

    public void afterThrowingAdvice(Exception exception) {
        System.out.println("===========after throwing advice exception:" + exception);
    }

    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========around before advice");
        Object retVal = pjp.proceed(new Object[] {"replace"});
        System.out.println("===========around after advice");
        return retVal;
    }*/
}
