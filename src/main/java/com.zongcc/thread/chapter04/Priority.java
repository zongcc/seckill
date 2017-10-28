package com.zongcc.thread.chapter04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 6-2
 * 默认优先级是5，优先级高的线程分

 配时间片的数量要多于优先级低的线程。设置线程优先级时，针对频繁阻塞（休眠或者I/O操

 作）的线程需要设置较高优先级，而偏重计算（需要较多CPU时间或者偏运算）的线程则设置较

 低的优先级，确保处理器不会被独占。在不同的JVM以及操作系统上，线程规划会存在差异，

 有些操作系统甚至会忽略对线程优先级的设定
 */
public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean notEnd   = true;

    public static void main(String[] args) throws Exception {
        List<Job> jobs = new ArrayList<Job>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority,"Thread:" + i);
            jobs.add(job);
            Thread thread = new Thread(job, "Thread:" + i);
            thread.setPriority(priority);
            thread.start();
        }
        notStart = false;//此刻开始count自增
        Thread.currentThread().setPriority(8);
        System.out.println(Thread.currentThread().getName()+" done.");
        TimeUnit.SECONDS.sleep(10);
        notEnd = false;

        for (Job job : jobs) {
            System.out.println("Job Priority : " + job.priority + ", Count : " + job.jobCount + ", name : " + job.name);
        }

    }

    static class Job implements Runnable {
        private int  priority;
        private long jobCount;
        private String name;

        public Job(int priority,String name) {
            this.priority = priority;
            this.name = name;
        }

        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (notEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
