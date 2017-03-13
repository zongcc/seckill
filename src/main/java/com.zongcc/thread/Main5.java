package com.zongcc.thread;

/**
 * Created by chunchengzong on 2017-01-12.
 */
public class Main5 {
    public static void main(String[] args) {

        PrintQueue printQueue = new PrintQueue();
        Thread thread[] = new Thread[10];
        for(int i=0;i<10;i++){
            thread[i] = new Thread(new Job(printQueue),"Thread--"+i);
        }
        for(int i=0;i<10;i++){
            thread[i].start();
        }
    }
}
