package com.zongcc.thread;

/**
 * Created by chunchengzong on 2017-04-01.
 */
public class InterruptDemo {
    public static void main(String[] args) {
        Thread t = new Thread();
        t.start();
        try {
            t.sleep(10000);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
