package com.zongcc.thread;

/**
 * @author chunchengzong
 * @date 2019-02-01 16:15
 **/
import java.util.Random;
import java.util.concurrent.*;

class Worker implements Runnable{
    private static int count = 0;
    private final int id = count++;
    private int finished = 0;
    private Random random = new Random(47);
    private Semaphore semaphore;
    public Worker(Semaphore semaphore){
        this.semaphore = semaphore;
    }

    @Override
    public void run(){
        try {
            while (!Thread.interrupted()){
                semaphore.acquire();
                System.out.println(this+" 占用一个机器在生产...   ");
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
                synchronized (this){
                    System.out.println(this + " 已经生产了"+(++finished)+"个产品,"+"释放出机器");
                }
                semaphore.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"-"+id;
    }
}

public class SemaphoreTest {
    public static void main(String[] args) {
        int N = 8;            //工人数
        Semaphore semaphore = new Semaphore(5); //机器数目
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < N; ++i){
            exec.execute(new Worker(semaphore));
        }
        exec.shutdown();
    }
}