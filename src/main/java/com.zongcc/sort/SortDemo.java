package com.zongcc.sort;

/**
 * Created by chunchengzong on 2016-04-18.
 */
public class SortDemo {

    public static void main(String[] args) {


        int a[]={
            49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18, 23, 34, 15, 35, 25, 53, 51
        } ;

        /*insertSort(a); //直接插入排序 时间复杂度：O（n^2）
        System.out.println("\n=========================");
        insertShellSort(a);//插入排序之希尔排序
        System.out.println("\n=========================");*/
        //simpleSelectionSort(a); //简单选择排序
        //heapSelectionSort(a); //堆排序
        bubbleSort(a); //冒泡排序


    }

    public static void bubbleSort(int[] a) {
        int len = a.length;
        for(int i=0;i<len-1;i++){
            for(int j=0;j<len-1-i;j++){
                if(a[j]>a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
            printArray(a);
            System.out.println();
        }
    }

    public static void heapSelectionSort(int[] list) {
        // 循环建立初始堆
        for (int i = list.length / 2; i >= 0; i--) {
            HeapAdjust(list, i, list.length - 1);
        }

        // 进行n-1次循环，完成排序
        for (int i = list.length - 1; i > 0; i--) {
            // 最后一个元素和第一元素进行交换
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;

            // 筛选 R[0] 结点，得到i-1个结点的堆
            HeapAdjust(list, 0, i);
            printArray(list);
        }
    }

    public static void  HeapAdjust(int[] array, int parent, int length) {
        int temp = array[parent]; // temp保存当前父节点
        int child = 2 * parent + 1; // 先获得左孩子

        while (child < length) {
            // 如果有右孩子结点，并且右孩子结点的值大于左孩子结点，则选取右孩子结点
            if (child + 1 < length && array[child] < array[child + 1]) {
                child++;
            }

            // 如果父结点的值已经大于孩子结点的值，则直接结束
            if (temp >= array[child])
                break;

            // 把孩子结点的值赋给父结点
            array[parent] = array[child];

            // 选取孩子结点的左孩子结点,继续向下筛选
            parent = child;
            child = 2 * child + 1;
        }

        array[parent] = temp;
    }

    public static void simpleSelectionSort(int[] a) {
        int len = a.length;
        /*for(int i=0;i<len;i++){
            int index = i;
            int temp = a[i];
            int mixValue = a[i];
            for (int j=i;j<len;j++){
                if(a[j]<mixValue){
                    mixValue = a[j];
                    index = j;
                }
            }
            a[i] = mixValue;
            a[index] = temp;
        }*/
        for(int i=0;i<len;i++){
            int index = i;
            for (int j=i;j<len;j++){
                if(a[j]<a[index]){
                    index = j;
                }
            }
            if(index != i) {
                int temp = a[i];
                a[i] = a[index];
                a[index] = temp;
            }
        }
        printArray(a);


    }

    public static void insertShellSort(int[] a) {
        int len = a.length;
        int k = len/2;
        while (k>=1){
            for(int i=k;i<len;i++){
                int temp = a[i];
                int j = i-k;
                while (j>=0 && a[j]>temp){
                    a[j+k] = a[j];
                    j=j-k;
                }
                a[j+k]=temp;
            }
            printArray(a);
            k=k/2;
        }
    }

    public static void insertSort(int[] a) {
        int len = a.length;
        for(int i=1;i<len;i++){
            int temp = a[i];
            int j = i-1;
            while (j>=0 && a[j]>temp){
                a[j+1]=a[j]; //将大于temp的值整体后移一个单位
                j--;
            }
            a[j+1]=temp;
            printArray(a);
        }
    }
    public static void printArray(int[] a) {
        System.out.print("\n");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] +" ");
        }
    }

}