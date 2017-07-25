package com.zongcc.leetcode.array;

import java.util.Arrays;
import java.util.Collections;

/**
 * 在一个有序数组里面移除重复value，并且返回新的数组长度
 * Created by chunchengzong on 2017-07-25.
 */
public class RemoveElementSortArray {
    public static void main(String[] args) {
        int[] a = new int[]{1,2,2,3,4,5};
        System.out.println(solution(a));
    }

    private static int solution(int[] a) {
        //Arrays.sort(a);//排序
        int length = a.length;
        int j = 0;
        for(int i=1;i<length;i++){
           if(a[i]!=a[j]){
               a[++j] = a[i];
           }
        }
        System.out.println(Arrays.toString(a));
        return j+1;
    }
}
