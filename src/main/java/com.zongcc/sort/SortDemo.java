package com.zongcc.sort;

/**
 * Created by zongcc on 2017/3/12.
 */
public class SortDemo {
    public static void main(String[] args) {
        int[] a = {1, 86, 2, 8, 9, 78, 53, 3, 24, 79};
        SortDemo sortDemo = new SortDemo();
        //sortDemo.insertSort(a);
        sortDemo.insertShellSort(a);
        printArray(a);
    }

    public static void printArray(int[] a) {
        System.out.println("\n +++++++++++++++++++++++++++++++");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    public void insertSort(int[] a) {
        if (a == null) {
            return;
        }
        int n = a.length;
        for (int i = 1; i < n; i++) {
            int j = i - 1;
            int temp = a[i];
            while ((j > -1) && a[j] > temp) {
                a[j + 1] = a[j];
                // a[j] = temp;
                j--;
            }
            a[j + 1] = temp;
        }

    }

    public void insertShellSort(int[] a) {
        if (a == null) {
            return;
        }
        int n = a.length;
        int k = n / 2;
        while (k >= 1) {
            for (int i = k; i < n; i++) {
                int temp = a[i];
                int j = i - k;
                while (j >= 0 && temp < a[j]) {
                    a[j + k] = a[j];
                    j = j - k;
                }
                a[j + k] = temp;
            }
            printArray(a);
            k = k / 2;
        }
    }
}
