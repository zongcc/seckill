package com.zongcc.sort;

/**
 * Created by chunchengzong on 2016-04-18.
 * 在要排序的一组数中，对当前还未排好序的范围内的全部数，
 * 自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，
 * 较小的往上冒。即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
 */
public class BubbleSort {
    public static void main(String[] args) {
        int a[] = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51};
        int temp = 0;
        int len = a.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }

            System.out.print("第" + (i + 1) + "次排序结果：");
            for (int k = 0; k < a.length; k++) {
                System.out.print(a[k] + "\t");
            }
            System.out.println("");
        }

        System.out.print("最终排序结果：");
        for (int j = 0; j < a.length; j++) {
            System.out.print(a[j] + "\t");
        }
    }
}