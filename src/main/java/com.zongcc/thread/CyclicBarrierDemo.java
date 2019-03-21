package com.zongcc.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chunchengzong
 * @date 2019-02-01 15:06
 **/

class CycTask implements Runnable {
    private static int count = 0;
    private final int id = count++;
    private CyclicBarrier barrier;
    private static Random random = new Random(47);
    public CycTask(CyclicBarrier cyclicBarrier) {
        this.barrier = cyclicBarrier;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            System.out.println(this+" 开始写入数据...");
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(5000));      //以睡眠来模拟写入数据操作
                System.out.println(this+" 写入数据完毕，等待其他线程写入完毕"+" "+System.currentTimeMillis());
                barrier.await();
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(this + "is interrupted!");
            }catch(BrokenBarrierException e){
                throw new RuntimeException(e);
            }
            System.out.println("所有任务写入完毕，继续处理其他任务... "+System.currentTimeMillis());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"-"+id;
    }
}

class CycManager implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private ExecutorService pool;

    CycManager(CyclicBarrier cyclicBarrier, ExecutorService pool, int times) {
        this.cyclicBarrier = cyclicBarrier;
        this.pool = pool;
        for (int i = 0; i < times; ++i) {
            pool.execute(new CycTask(cyclicBarrier));
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                cyclicBarrier.await();
            }catch (InterruptedException e){
                System.out.println(getClass().getSimpleName()+" 被中断了！");
            }catch (BrokenBarrierException e){
                throw new RuntimeException(e);
            }
        }
    }
}

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        int times = 2;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(new CycManager(cyclicBarrier, pool, times));
        pool.shutdown();
    }
}