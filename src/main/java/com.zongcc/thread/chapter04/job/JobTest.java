package com.zongcc.thread.chapter04.job;

import com.zongcc.thread.chapter04.SleepUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chunchengzong on 2017-06-26.
 */
public class JobTest {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch downLatch = new CountDownLatch(3);
        DefaultThreadPool defaultThreadPool = new DefaultThreadPool(2);
        defaultThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    System.out.println(Thread.currentThread().getName()+"--"+i);
                }
                //SleepUtils.second(1);
                downLatch.countDown();
            }
        });
        defaultThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    System.out.println(Thread.currentThread().getName()+"--"+i);
                }
                //SleepUtils.second(1);
                downLatch.countDown();
            }
        });
        defaultThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<100;i++){
                    System.out.println(Thread.currentThread().getName()+"--"+i);
                }
                //SleepUtils.second(1);
                downLatch.countDown();
            }
        });
        System.out.println(defaultThreadPool.getJobSize());
        downLatch.await();
        SleepUtils.second(1);
        defaultThreadPool.shutdown();
    }
}
