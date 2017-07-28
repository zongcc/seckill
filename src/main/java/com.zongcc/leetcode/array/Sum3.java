package com.zongcc.leetcode.array;

import com.alibaba.dubbo.common.utils.CollectionUtils;

import java.util.*;

/**
 * 给定一个数组和一个值，让求出这个数组中两个值的和等于这个给定值的坐
 标。输出是有要求的，1，	坐标较小的放在前面，较大的放在后面。2，	这俩坐标不能为零。
 * Created by chunchengzong on 2017-07-26.
 */
public class Sum3 {


    public static void main(String[] args) {
        int[] a = new int[]{3,-6,3,6,0};
        System.out.println(solutionMap(a));
    }

    //时间复杂度O(n),空间复杂度O(n)
    private static Set<List<Integer>> solutionMap(int[] nums) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            map.put(i,nums[i]);
        }
        Set<List<Integer>> list =new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j=i+1;j<nums.length;j++){
                int ano = -(nums[i]+nums[j]);
                if(map.containsValue(ano)){
                    List<Integer> row = Arrays.asList(map.get(i),map.get(j),ano);
                    list.add(CollectionUtils.sort(row));
                }
            }
        }
        return list;
    }


}
