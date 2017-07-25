package com.zongcc.leetcode.array;

import java.util.Arrays;

/**
 * 在一个有序数组里面移除重复value,最多保留两个相同元素，并且返回新的数组长度
 * Created by chunchengzong on 2017-07-25.
 */
public class RemoveElementSortArrayII {
    public static void main(String[] args) {
        int[] a = new int[]{1,1,1,3,3,5};
        System.out.println(solution(a));
    }

    private static int solution(int[] a) {
        //Arrays.sort(a);//排序
        int length = a.length;
        int j = 0;
        int num = 0;
        for(int i=1;i<length;i++){
           if(a[j]==a[i]){
               num++;
               if(num<2){
                   a[++j] = a[i];
               }
           }else {
               a[++j] = a[i];
               num = 0;
           }
        }
        System.out.println(Arrays.toString(a));
        return j+1;
    }
}
