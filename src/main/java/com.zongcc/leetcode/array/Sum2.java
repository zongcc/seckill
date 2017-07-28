package com.zongcc.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个数组和一个值，让求出这个数组中两个值的和等于这个给定值的坐
 标。输出是有要求的，1，	坐标较小的放在前面，较大的放在后面。2，	这俩坐标不能为零。
 * Created by chunchengzong on 2017-07-26.
 */
public class Sum2 {


    public static void main(String[] args) {
        int[] a = new int[]{3,3};
        System.out.println(Arrays.toString(solution(a,6)));
        System.out.println(Arrays.toString(solutionMap(a,6)));
    }

    //时间复杂度O(n),空间复杂度O(n)
    private static int[] solutionMap(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement) && map.get(complement) != i) {
                return new int[] { i, map.get(complement) };
            }
        }
        return null;
    }

    //时间复杂度O(n2)
    private static int[] solution(int[] a, int b) {
        for (int i=0;i<a.length;i++){
            for(int j=i+1;j<a.length;j++){
                if(a[i]+a[j] == b){
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }


}
