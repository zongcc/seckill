package com.zongcc.stream;

import com.zongcc.utils.JacksonUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chunchengzong
 * @date 2018-12-04 13:53
 **/
public class StreamDemo {

    static class Widgets {
        private String color;

        private Integer weight;

        public Widgets(String color, Integer weight) {
            this.color = color;
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }
    }

    public static void main(String[] args) {

        List<Widgets> widgets = new ArrayList<>();
        widgets.add(new Widgets("RED", 200));
        widgets.add(new Widgets("RED", 100));
        widgets.add(new Widgets("RED", 150));
        widgets.add(new Widgets("YELLOW", 250));
        widgets.add(new Widgets("BLUE", 300));
        widgets.add(new Widgets("orange", 350));
        widgets.add(new Widgets("green", 400));
        widgets.add(new Widgets("purple", 450));
        widgets.add(new Widgets("pink", 500));
        widgets.add(new Widgets("white", 550));
        widgets.add(new Widgets("black", 600));

        List<Integer> statsNumbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = statsNumbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
        statsNumbers.stream().forEach(System.out::println);
        System.out.println("========================================================");
        statsNumbers.parallelStream().forEach(System.out::println);
        System.out.println("========================================================");
        statsNumbers.parallelStream().forEachOrdered(System.out::println);

        System.out.println("========================================================");

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        // 获取空字符串的数量
        long count = strings.parallelStream().filter(string -> !string.isEmpty()).count();
        System.out.println("not empty count ===== >" + count);
        String mergeStr = strings.parallelStream().filter(string -> !string.isEmpty()).collect(Collectors.joining(","));
        System.out.println("mergeStr===== >" + mergeStr);



        widgets.stream().filter(t -> t.getColor().equals("RED")).sorted((x,y)->y.weight-x.getWeight()).mapToInt(Widgets::getWeight).forEach
                (System.out::println);

        System.out.println("========================================================");

        widgets.stream().map(Widgets::getWeight).skip(2).limit(8).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================================================");

        widgets.parallelStream().filter(t -> t.getColor().equals("RED")).sorted(Comparator.comparing(Widgets::getWeight))
                .map(Widgets::getWeight).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================================================");

        widgets.parallelStream().map(w -> w.getColor().toLowerCase()).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================================================");

        widgets.parallelStream().map(w -> w.getWeight() * w.getWeight()).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("========================================================");

        int sum = widgets.stream().filter(w -> w.getColor().equals("RED")).mapToInt(w -> w.getWeight()).sum();
        System.out.println(sum);

        System.out.println("========================================================");

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.println("squaresList ==== >" + JacksonUtil.toJson(squaresList));

        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );

        inputStream.flatMap((childList) -> childList.stream()).collect(Collectors.toList()).forEach(System.out::println);

        Stream.of("one", "two", "three", "four")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0).toArray(Integer[]::new);
        System.out.println("偶数======>" + Arrays.toString(evens));

        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println("concat====>" + concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println("min====>" + minValue);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println("sumValue====>" + sumValue);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        System.out.println("sumValue====>" + sumValue);
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
        System.out.println("concat====>" + concat);


        //构造流的几种常见方法
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        //数值流的构造
        /*IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);*/

        /*//流转换为其它数据结构
        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();*/

    }
}