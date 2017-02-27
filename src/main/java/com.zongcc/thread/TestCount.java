package com.zongcc.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zongcc on 2016/12/22.
 */
public class TestCount {

    public static void main(String[] args) throws Exception {

        CountDownLatch threadSignal = new CountDownLatch(2);//初始化countDown
        JoinTest r = new JoinTest(threadSignal);
        Thread t = new Thread(r);
        t.start();

        JoinTest r2 = new JoinTest(threadSignal);
        Thread t2= new Thread(r);
        t2.start();
        threadSignal.await();//等待所有子线程执行完
        System.out.println(r.getRs()+r2.getRs());


        ExecutorService executorService= Executors.newFixedThreadPool(5);
        List<Future<Integer>>resultList=new ArrayList<Future<Integer>>();
        for (int i = 0; i < 100; i++){
            //使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<Integer> future = executorService.submit(new TaskWithResult(i));
            //将任务执行结果存储到List中
            resultList.add(future);
        }

        Integer total = 0;
        for (Future<Integer> fs : resultList){
            try{
                while(!fs.isDone());//Future返回如果没有完成，则一直循环等待，直到Future返回完成
                total +=fs.get();
            }catch(InterruptedException e){
                e.printStackTrace();
            }catch(ExecutionException e){
                e.printStackTrace();
            }finally{
                //启动一次顺序关闭，执行以前提交的任务，但不接受新任务
                executorService.shutdown();
            }
        }
        System.out.println(total);


    }

}