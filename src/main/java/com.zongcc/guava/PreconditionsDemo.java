package com.zongcc.guava;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Preconditions 优雅的检验参数
 * Created by chunchengzong on 2017-03-24.
 */
public class PreconditionsDemo {

    public static void main(String[] args) {
        //checkValid(1,null); //Exception in thread "main" java.lang.NullPointerException: name is null
        //checkValid(300,"zcc");//Exception in thread "main" java.lang.IllegalArgumentException: age is invalid
        checkValid(100,"zcc");

        List<Integer> intList=new ArrayList<Integer>();
        for(int i=0;i<10;i++){
            try {
                checkState(intList,9);
                intList.add(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        try {
            checkPositionIndex(intList,13);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            checkPositionIndexes(intList,3,17);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        try {
            checkElementIndex(intList,16);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public static void checkValid(Integer age,String name) {
        Preconditions.checkNotNull(name,"name is null");
        Preconditions.checkArgument((age>0&&age<200),"age is invalid");
    }
    public static void checkState(List<Integer> intList, int index)throws Exception{
        //表达式为true不抛异常
        Preconditions.checkState(intList.size()<index, " intList size 不能大于"+index);
    }

    public static void checkPositionIndex(List<Integer> intList,int index) throws Exception{
        Preconditions.checkPositionIndex(index, intList.size(), "index "+index+" 不在 list中， List size为："+intList.size());
    }

    public static void checkPositionIndexes(List<Integer> intList,int start,int end) throws Exception{
        Preconditions.checkPositionIndexes(start, end, intList.size());
    }

    public static void checkElementIndex(List<Integer> intList,int index) throws Exception{
        Preconditions.checkElementIndex(index, intList.size(),"index 为 "+index+" 不在 list中， List size为： "+intList.size());
    }
}
