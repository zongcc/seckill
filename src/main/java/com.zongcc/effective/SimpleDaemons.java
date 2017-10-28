package com.zongcc.effective;

import java.util.concurrent.TimeUnit;

/**
 * Created by chunchengzong on 2017-09-15.
 */
public class SimpleDaemons implements Runnable {
    @Override
    public void run() {
        while (true){
            try {
                TimeUnit.MILLISECONDS.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("execute finally method .......");
            }
            System.out.println(Thread.currentThread() + ""+this);
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            Thread thread = new Thread(new SimpleDaemons());
            thread.setDaemon(false);
            thread.start();

        }
        System.out.println("all start >........");
    }
}
