package com.zongcc.effective;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现了<? extends T>的集合类只能将它视为Producer向外提供(get)元素，而不能作为Consumer来对外获取(add)元素。
 * add元素应该怎么做呢？可以使用<? super T>
 * Created by chunchengzong on 2017-09-19.
 */
public class testGeneric {
    static List<Apple> apples = new ArrayList<Apple>();
    static List<Fruit> fruit = new ArrayList<Fruit>();
    static <T> void writeExact(List<T> list, T item) {
        list.add(item);
        System.out.println("writeExact "+item);
    }
    static void f1() {
        writeExact(apples, new Apple());
        writeExact(fruit, new Orange());
    }
    static <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
        System.out.println("writeWithWildcard "+item);
    }
    static void f2() {
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruit, new Orange());
    }

    static void getRead(List<? extends Fruit> list) {
        for (int i=0;i<list.size();i++){
            System.out.println("getRead "+list.get(i));
        }
    }
    static void f3() {
        getRead(apples);
        getRead(fruit);
    }
    public static void main(String[] args) {
        f1(); f2();f3();
    }
}

class Fruit {}
class Apple extends Fruit {}
class Orange extends Fruit {}