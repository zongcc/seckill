package com.zongcc.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chunchengzong on 2017-01-12.
 */
public class PrintQueue {
    private final Lock queueLock = new ReentrantLock();
    public void printJob(Object document){
        queueLock.lock();//获取Lock对象的控制权

        try {
            Long duration = (long)(Math.random()*10000);
            System.out.println(Thread.currentThread().getName()
                    +"PrintQueue:Printing a Job during "
                    +(duration/1000)+" seconds");
            Thread.sleep(duration);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            queueLock.unlock();//释放Lock对象的控制
        }
    }
}
