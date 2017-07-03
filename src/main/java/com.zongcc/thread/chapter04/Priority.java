package com.zongcc.thread.chapter04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 6-2
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
