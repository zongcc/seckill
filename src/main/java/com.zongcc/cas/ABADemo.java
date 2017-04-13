package com.zongcc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by chunchengzong on 2017-03-14.
 */
public class ABADemo {
    private static AtomicInteger atomicInt = new AtomicInteger(100);
    private static AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);
    public static void main(String[] args) throws InterruptedException {
        Thread intT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }
        });
        Thread intT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicInt.compareAndSet(100, 101);
                System.out.println(c3); // true
            }
        });
        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();
        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            }
        });
        Thread refT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int stamp = atomicStampedRef.getStamp();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(c3); // false
            }
        });
        refT1.start();
        refT2.start();


        ABADemo abaDemo = new ABADemo();
        System.out.println(abaDemo.reverseString("hello"));
        System.out.println("11&10 = "+ (11&10)); //1011&1010=1010

        System.out.println(finallyTest());


    }

    //finally如果有return总是返回3，否则异常返回2，不异常返回1，System.exit(0);直接退出
    public static String finallyTest() {
        try {
            System.out.println("test==================try"); //1011&1010=1010
            int i=1/0;
            return "1";
        }catch (Exception e){
            System.out.println("test==================catch"); //1011&1010=1010
            System.exit(0);//调换异常
            return "2";
        }finally {
            System.out.println("test==================finally"); //1011&1010=1010
            return "3";
        }
    }

        public static String reverseString(String s) {
            int len = s.length();
            char[] ca = new char[len+1];
            //System.arraycopy(s,0,ca,0,len);
            ca = s.toCharArray();
            StringBuffer sb = new StringBuffer();
            for(int i=len-1;i>=0;i--){
                sb.append((ca[i]));
            }
            return sb.toString();
        }

}

