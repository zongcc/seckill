package com.zongcc.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by zongcc on 2016/12/22.
 */
public class JoinTest implements Runnable{
    private volatile int a=0;
    private CountDownLatch threadsSignal;

    public JoinTest(CountDownLatch threadsSignal) {
        this.threadsSignal = threadsSignal;
    }
    @Override
    public void run() {
        for (int k = 0; k < 100; k++) {
            a = a + 1;
        }
        System.out.println(Thread.currentThread().getName()+"---running...............");
        threadsSignal.countDown();
    }
    public int getRs(){
        return a;
    }

}
