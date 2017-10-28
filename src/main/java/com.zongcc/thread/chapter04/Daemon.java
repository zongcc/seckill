package com.zongcc.thread.chapter04;

/**
 * 6-5
 */
public class Daemon {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner());
        thread.setDaemon(true);
        thread.start();
        /*
        启动了线程DaemonRunner之后随着main方法执行完毕而终止，
        而此时Java虚拟机中已经没有非Daemon线程，虚拟机需要退出
        有Daemon线程都需要立即终止
         */
        //SleepUtils.second(10);
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(1);
            } finally {
                //不一定执行，关闭和清理资源不一定有效
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
