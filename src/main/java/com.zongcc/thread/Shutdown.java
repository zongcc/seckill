package com.zongcc.thread;

import java.util.concurrent.TimeUnit;

/**
 * 如何停止一个线程
 * Created by chunchengzong on 2017-06-19.
 */
public class Shutdown {
    public static void main(String[] args) throws InterruptedException {
        Runner one = new Runner();
        Thread oneThread = new Thread(one,"oneThread");
        oneThread.start();
        TimeUnit.SECONDS.sleep(1);
        oneThread.interrupt();

        Runner two = new Runner();
        oneThread = new Thread(two,"twoThread");
        oneThread.start();
        TimeUnit.SECONDS.sleep(1);
        two.cancel();

    }

    private static class Runner implements Runnable{
        private long i;
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("count i = "+i);
        }

        public void cancel(){
            on = false;
        }
    }
}
