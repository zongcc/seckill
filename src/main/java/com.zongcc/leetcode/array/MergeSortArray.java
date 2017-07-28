package com.zongcc.leetcode.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chunchengzong on 2017-07-26.
 */
public class MergeSortArray {


    public static void main(String[] args) {
        int[] a = new int[]{1,2,21,31,41,51};
        int[] b = new int[]{2,5,7,8,9,14,15};
        System.out.println(Arrays.toString(solution(a,b)));
    }

    private static int[] solution(int[] a, int[] b) {
        int al = a.length-1;//A数组长度，0开始减一
        int bl = b.length-1;//B数组长度，0开始减一
        int m = a.length+b.length-1;//总和数组长度，0开始减一
        int[] len = new int[m+1];
        while (m>=0){
            if(al>=0&&bl>=0){
                if(a[al]>b[bl]){
                    len[m] = a[al];
                    al--;
                }else {
                    len[m] = b[bl];
                    bl--;
                }
            }else if(al>=0){
                len[m] = a[al];
                al--;
            }else if(bl>=0){
                len[m] = b[bl];
                bl--;
            }
            m--;
        }
        return len;
    }


}
