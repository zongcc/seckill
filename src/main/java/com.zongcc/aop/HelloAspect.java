package com.zongcc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by chunchengzong on 2016-09-28.
 */
@Aspect
public class HelloAspect {
    @Pointcut(value = "execution(* com.zongcc.aop..*.*(..)) && args(param)", argNames = "param") //注意此处的连接不是and而是&&
    public void beforePointcut(String param) {}
    @Pointcut(value = "execution(* com.zongcc.aop..*.*(..))") //
    public void afterPointcut() {}

    @Before(value = "beforePointcut(param)", argNames = "param")
    public void beforeAdvice(String param) {
        System.out.println("===========HelloAspect===before advice param:" + param);
    }
    @AfterReturning(
            pointcut="afterPointcut()",
            argNames="retVal", returning="retVal")
    public void afterReturningAdvice(Object retVal) {
        System.out.println("========HelloAspect===after returning advice retVal:" + retVal);
    }

    @AfterThrowing(
            value="afterPointcut()",
            argNames="exception", throwing="exception")
    public void afterThrowingAdvice(Exception exception) {
        System.out.println("===========after throwing advice exception:" + exception);
    }

    @After(value="afterPointcut()")
    public void afterFinallyAdvice() {
        System.out.println("===========after finally advice");
    }

    @Around(value="afterPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("===========around before advice");
        Object retVal = pjp.proceed(new Object[] {"replace"});
        System.out.println("===========around after advice");
        return retVal;
    }
}
