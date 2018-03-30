package com.zongcc.staticTest;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @author chunchengzong
 * @date 2018-02-22 14:01
 **/
public class WeakReferenceDemo {
    public static void main(String[] args) throws IOException, InterruptedException {
        final ReferenceQueue queue = new ReferenceQueue();
        new Thread(new Runnable(){

            @Override
            public void run() {
                while (true) {
                    try {
                        Reference reference = queue.remove();
                        System.out.println(reference + "回收了"+System.currentTimeMillis());
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();

        Object o = new Object();
        Object o2 = new Object();
        Reference root = new WeakReference(o, queue);
        Reference root2 = new WeakReference(o2, queue);
        System.out.println(root);
        System.out.println(root2);
        o = null;
        o2 = null;
        System.gc();
        System.in.read();
    }
}