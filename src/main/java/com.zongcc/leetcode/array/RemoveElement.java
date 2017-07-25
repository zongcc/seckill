package com.zongcc.leetcode.array;

import java.util.Arrays;

/**
 * 在一个数组里面移除指定value，并且返回新的数组长度
 * Created by chunchengzong on 2017-07-25.
 */
public class RemoveElement {
    public static void main(String[] args) {
        int[] a = new int[]{1,2,2,3,2,4,5};
        System.out.println("====="+solution(a,2));
        System.out.println(removeElement(a,2));
    }

    private static int solution(int[] a, int elemt) {
        int length = a.length;
        int i = 0;
        int j = 0;
        for(i=0;i<length;i++){
            if(a[i] == elemt){
                continue;
            }
            a[j] = a[i];
            j++;
        }
        System.out.println(Arrays.toString(a));
        return j;
    }

    public static int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        System.out.println(Arrays.toString(nums));
        return i;
    }
}
