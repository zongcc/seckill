package com.zongcc.effective;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by chunchengzong on 2017-09-15.
 */
public class LiftOff implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++; //final初始化为0

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "liftOff!") + ").";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.print(status());
            //Thread.yield();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        LiftOff liftOff = new LiftOff();
//        liftOff.run();
//        System.out.println();
//        System.out.println("five thread=================");
//        for(int i=0;i<5;i++){
//            new Thread(new LiftOff()).start();
//        }
//        System.out.println("wait for five thread running !!");

//        ExecutorService executorService = Executors.newCachedThreadPool();
//        for(int i=0;i<5;i++){
//            executorService.execute(new LiftOff());
//        }
//        executorService.shutdown();

//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        for(int i=0;i<5;i++){
//            executorService.execute(new LiftOff());
//        }
//        executorService.shutdown();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for(int i=0;i<5;i++){
            executorService.execute(new LiftOff());
        }
        executorService.shutdown();

    }
}
