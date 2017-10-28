package com.zongcc.leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chunchengzong on 2017-10-16.
 */
public class SameTwoArrays {
    public static void main(String[] args) {
        //两个数组相同元素
        String as[]={"1","3","5","5","7","8","5","4","3","a"},
                bs[]={"1","2","3","4","8","66","6","5","5","10","a"};
        HashSet<String> sa=new HashSet<String>(Arrays.asList(as));
        sa.retainAll(Arrays.asList(bs));
        System.out.println(sa);

        System.out.println(getSame(as,bs));
    }

    public static Set<String> getSame(String[] as,String[] bs){
        Set<String> same = new HashSet<String>();
        Set<String> temp = new HashSet<String>();
        for(int i=0;i<as.length;i++){
            temp.add(as[i]);
        }
        for(int j=0;j<bs.length;j++){
            if(!temp.add(bs[j])){
                same.add(bs[j]);
            }
        }
        return same;
    }
}
