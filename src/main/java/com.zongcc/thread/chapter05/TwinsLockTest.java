package com.zongcc.thread.chapter05;

import com.zongcc.thread.chapter04.SleepUtils;

import java.util.concurrent.locks.Lock;

/**
 * Created by chunchengzong on 2017-06-29.
 */
public class TwinsLockTest {
    public void test() {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName()+" -----------");
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println("=======================");
        }
    }

    public static void main(String[] args) {
        TwinsLockTest twinsLockTest = new TwinsLockTest();
        twinsLockTest.test();
    }
}
