package com.zongcc.effective;

/**
 * Created by chunchengzong on 2017-09-18.
 */
public class Joiner extends Thread {
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        start();
    }
    public void run(){
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println(getName()+" was joiner Interrupted");
            return;
        }
        System.out.println(getName() + " has completed");
    }

    public static void main(String[] args) {
        Sleeper sleepy = new Sleeper("sleepy",1500);
        Sleeper grumpy = new Sleeper("grumpy",1500);
        Joiner dopey = new Joiner("dopey",sleepy);
        Joiner doc = new Joiner("doc",grumpy);
        grumpy.interrupt();
        //doc.interrupt();
    }
}
