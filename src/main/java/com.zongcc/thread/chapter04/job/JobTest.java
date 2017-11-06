package com.zongcc.thread.chapter04.job;

import com.zongcc.thread.chapter04.SleepUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by chunchengzong on 2017-06-26.
 */
public class JobTest {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch downLatch = new CountDownLatch(1);
        DefaultThreadPool defaultThreadPool = new DefaultThreadPool(3);
        defaultThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    downLatch.await();
                } catch (InterruptedException e) {

                }
                for (int i=0;i<100;i++){
                    System.out.println(Thread.currentThread().getName()+"-11-"+i);
                }

            }
        });
        defaultThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    downLatch.await();
                } catch (InterruptedException e) {

                }
                for (int i=0;i<100;i++){
                    System.out.println(Thread.currentThread().getName()+"-22-"+i);
                }
            }
        });
        defaultThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    downLatch.await();
                } catch (InterruptedException e) {

                }
                for (int i=0;i<100;i++){
                    System.out.println(Thread.currentThread().getName()+"-33-"+i);
                }
            }
        });
        System.out.println("Job数量： "+defaultThreadPool.getJobSize());
        SleepUtils.second(10);
        System.out.println("main end ...............................................................");
        downLatch.countDown();
        defaultThreadPool.shutdown();
    }
}
