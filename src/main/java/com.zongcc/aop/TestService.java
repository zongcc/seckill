package com.zongcc.aop;

/**
 * Created by chunchengzong on 2016-09-27.
 */
public class TestService {
    public String helloWorld(){
        try {
            System.out.println(getClass().getClassLoader().getClass()+"======================helloWorld===============================");
            //int i = 1/0;
            return "true";
        }catch (Exception e){
            e.printStackTrace();
        }
        return "false";
    }

  /*  public void sayBefore(String param) {
        System.out.println("============say " + param);
    }

    public boolean sayAfterReturning() {
        System.out.println("============after returning");
        return true;
    }

    public void sayAfterThrowing() {
        System.out.println("============before throwing");
        throw new RuntimeException();
    }

    public boolean sayAfterFinally() {
        System.out.println("============before finally");
        throw new RuntimeException();
    }

    public void sayAround(String param) {
        System.out.println("============around param:" + param);
    }

    public void sayAdvisorBefore(String param) {
        System.out.println("============say " + param);
    }*/
}
