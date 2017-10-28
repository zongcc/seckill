package com.zongcc.effective;

import com.zongcc.thread.chapter04.Synchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chunchengzong on 2017-09-18.
 */
public class AtomicityTest implements Runnable {
    private volatile int i = 0;
    public int getValue(){return i;} //不是同步方法
    private synchronized void increment(){i++;i++;}
    @Override
    public void run() {
        while (true){increment();}
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        executorService.execute(at);
        while (true){
            int val = at.getValue();
            if(val % 2 != 0){
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
