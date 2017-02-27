package com.zongcc.thread;

import org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by zongcc on 2016/12/31.
 */
public class ForkJoinTest {
    public static class Calculator extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 100;
        private int start;
        private int end;
        int sum = 0;

        public Calculator(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if((end - start) < THRESHOLD){
                for(int i = start; i< end;i++){
                    sum += i;
                }
            }else{
                int middle = (start + end) /2;
                Calculator left = new Calculator(start, middle);
                Calculator right = new Calculator(middle, end);
                left.fork();
                right.fork();
                sum = left.join() + right.join();
            }
            return sum;
        }

    }
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        try {
            int n =pool.invoke(new Calculator(0, 100));
            System.out.println("invoke method result is "+n);
            ForkJoinTask<Integer> submit = pool.submit(new Calculator(0, 100));
            System.out.println("submit method result is "+submit.get());
            pool.execute(new Calculator(0,1000));
            pool.execute(new Runnable() {
                int total = 0;
                @Override
                public void run() {
                    for (int i=0;i<100;i++){
                        total +=i;
                    }
                    System.out.print("execute method result is "+total);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            if(pool.isShutdown()){
                pool.shutdownNow();
            }else {
                pool.shutdown();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(" cost time "+(end-start));
    }
}
