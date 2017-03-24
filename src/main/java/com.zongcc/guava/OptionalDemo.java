package com.zongcc.guava;


import com.google.common.base.Optional;

/**
 *  List：允许重复元素，可以加入任意多个null。
    Set：不允许重复元素，最多可以加入一个null。
    Map：Map的key最多可以加入一个null，value字段没有限制。
    数组：基本类型数组，定义后，如果不给定初始值，则java运行时会自动给定值。引用类型数组，不给定初始值，则所有的元素值为null。
 * Created by chunchengzong on 2017-03-24.
 */
public class OptionalDemo {


    public static void main(String[] args) {
        //内部包含了一个非null的T数据类型实例，若T=null，则立刻报错
        Optional<Integer> op = Optional.of(10);
        //将一个T的实例转换为Optional对象，T的实例可以不为空，也可以为空
        Optional<Integer> NullableOpt=Optional.fromNullable(null);
        Optional<Integer> NoNullableOpt=Optional.fromNullable(11);

        //获得一个Optional对象，其内部包含了空值
        if(op.isPresent()){
            System.out.println("op.get()============>"+ op.get());
        }
        //如果为空直接获取报错：Exception in thread "main" java.util.NoSuchElementException: No value present
        //System.out.println("fromNullableOpt isPresent:"+NullableOpt.get());
        //orElse: Return the value if present, otherwise return {@code other}
        System.out.println("fromNullableOpt isPresent value :"+NullableOpt.or(12));
        if(NullableOpt.isPresent()){
            System.out.println("fromNullableOpt isPresent:"+NullableOpt.isPresent());
        }
        if(NoNullableOpt.isPresent()){
            System.out.println("NoNullableOpt isPresent:"+NoNullableOpt.isPresent());
            System.out.println("NoNullableOpt isPresent value :"+NoNullableOpt.get());
            System.out.println("NoNullableOpt isPresent value :"+NoNullableOpt.or(12));
        }
    }
}
