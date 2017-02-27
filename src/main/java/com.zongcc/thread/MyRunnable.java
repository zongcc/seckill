package com.zongcc.thread;

/**
 * Created by zongcc on 2016/12/25.
 */
public class MyRunnable implements Runnable {
    private String name;

    public MyRunnable(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name+" is running....................");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
