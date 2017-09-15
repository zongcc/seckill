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
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + ""+this);
        }
    }

    public static void main(String[] args) {

    }
}
