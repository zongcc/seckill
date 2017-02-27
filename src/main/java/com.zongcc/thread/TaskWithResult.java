package com.zongcc.thread;

import java.util.concurrent.Callable;

/**
 * Created by zongcc on 2016/12/22.
 */
public class TaskWithResult implements Callable {
    public TaskWithResult(int i){
        //System.out.println("----第"+i+"个线程");
    }


    @Override
    public Object call() throws Exception {
        int a=0;
        for (int k = 0; k < 5; k++) {
            a = a + 1;
        }
        System.out.println("当前线程名称"+Thread.currentThread().getName());
        return a;
    }
}
