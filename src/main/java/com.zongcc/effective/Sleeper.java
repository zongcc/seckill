package com.zongcc.effective;

/**
 * Created by chunchengzong on 2017-09-18.
 */
public class Sleeper extends Thread {
    private int duration;
    public Sleeper(String name,int sleepTime){
        super(name);
        duration = sleepTime;
        start();
    }
    public void run(){
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName()+" was sleep Interrupted");
            return;
        }
        System.out.println(getName() + " has awakend");
    }
}
