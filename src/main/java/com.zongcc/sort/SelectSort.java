package com.zongcc.sort;

/**
 * Created by chunchengzong on 2016-04-18.
 * 基本思想：在要排序的一组数中，
 * 选出最小的一个数与第一个位置的数交换；
   然后在剩下的数当中再找最小的与第二个位置的数交换，
   如此循环到倒数第二个数和最后一个数比较为止。
 */
public class SelectSort {
    public static void main(String[] args){
        int a[]={1,54,6,3,78,34,12,45};
        int position=0;
        int len = a.length;
        for(int i=0;i<len;i++){
            int j = i+1;
            position = i;
            int temp = a[i];
            for(;j<len;j++){
                if(a[j]<temp){
                   temp = a[j];
                   position = j;
                }
            }
            a[position] = a[i];
            a[i] = temp;
        }

        for(int i=0;i<a.length;i++)
        System.out.println(a[i]);
    }
}
