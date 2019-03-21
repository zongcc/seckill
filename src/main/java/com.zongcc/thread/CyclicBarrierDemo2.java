package com.zongcc.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chunchengzong
 * @date 2019-02-02 14:30
 **/

class RunThread implements Runnable{
    CyclicBarrier cyclicBarrier;
    int num;

    public RunThread(CyclicBarrier barrier,int num){
        this.cyclicBarrier = barrier;
        this.num = num;
    }


    @Override
    public void run() {
        System.out.println(num + "号运动员已经准备好，等待开枪！！！");
        try {
            Thread.currentThread().sleep(new Random(100).nextInt(5000));
            cyclicBarrier.await();
            System.out.println(num + "号运动员已完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
public class CyclicBarrierDemo2 {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("-------开枪---------跑!!!"));

        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(() -> {
            for (int i=0;i<5;i++){
                new Thread(new RunThread(cyclicBarrier,i)).start();
            }

        });
        pool.execute(() -> {
            for (int i=5;i<10;i++){
                new Thread(new RunThread(cyclicBarrier,i)).start();
            }

        });

        pool.shutdown();

//        for (int i=0;i<5;i++){
//            new Thread(new RunThread(cyclicBarrier,i)).start();
//        }

    }
}