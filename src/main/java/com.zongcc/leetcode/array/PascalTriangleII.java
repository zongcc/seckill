package com.zongcc.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 帕斯卡三角给出高度直接返回高度的数据列表
 * Created by chunchengzong on 2017-07-25.
 */
public class PascalTriangleII {
    public static void main(String[] args) {
        System.out.println(generate(6));
    }

    public static List<Integer> generate(int numRows) {
        ArrayList<Integer> row = new ArrayList<Integer>();
        for(int i=0;i<numRows;i++)
        {
            row.add(0, 1);
            for(int j=1;j<row.size()-1;j++)
                row.set(j, row.get(j)+row.get(j+1));
        }
        return row;
    }
}
