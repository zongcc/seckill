package com.zongcc.thread;

import java.util.concurrent.*;

/**
 * Created by zongcc on 2016/12/25.
 */
public class DiscardPolicyDemo {
    private static final int THREADS_SIZE = 1;
    private static final int CAPACITY = 1;

    public static void main(String[] args) throws Exception {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE, THREADS_SIZE, 0, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(CAPACITY));
        // 设置线程池的拒绝策略为"丢弃"(不能执行的任务将被删除;)
        //pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        //设置线程池的拒绝策略为"DiscardOldestPolicy"( 如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）)
        //pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        // (默认)设置线程池的拒绝策略为"抛出异常"(处理程序遭到拒绝将抛出运行时 RejectedExecutionException)
        //pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        // 设置线程池的拒绝策略为"CallerRunsPolicy"(线程调用运行该任务的 execute 本身。此策略提供简单的反馈控制机制，能够减缓新任务的提交速度)
        //pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 新建10个任务，并将它们添加到线程池中。
        for (int i = 0; i < 10; i++) {
            Runnable myrun = new MyRunnable("task-"+i);
            pool.execute(myrun);
        }
        // 关闭线程池
        pool.shutdown();
    }
}
