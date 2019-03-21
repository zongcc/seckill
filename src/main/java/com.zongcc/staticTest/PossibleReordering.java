package com.zongcc.staticTest;

/**
 * @author chunchengzong
 * @date 2018-08-30 19:30
 **/
public class PossibleReordering {
    static int x = 0, y = 0;
    static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
            public void run() {
                a = 1;
                x = b;
            }
        });

        Thread other = new Thread(new Runnable() {
            public void run() {
                b = 1;
                y = a;
            }
        });
        one.start();
        other.start();
        one.join();
        other.join();
        System.out.println("("+x + ","+y + ")");
        //有可能出现(0,0)重排序的结果


        int x, y;
        x = 1;
        try {
            x = 2;
            //为了保证最终不致于输出x = 1的错误结果，JIT在重排序时会在catch语句中插入错误代偿代码，将x赋值为2，
            // 将程序恢复到发生异常时应有的状态。
            y = 0 / 0;
        } catch (Exception e) {
        } finally {
            System.out.println("x = " + x);
        }
    }
}