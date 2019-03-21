package com.zongcc.staticTest;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author chunchengzong
 * @date 2018-12-29 16:49
 **/
public class Java8 {

    interface Converter<F, T> {
        T convert(F from);
    }



    public static void main(String[] args) {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
        Collections.sort(names, String::compareTo);
        Collections.sort(names, Comparator.naturalOrder());
        Collections.sort(names, Comparator.reverseOrder());
        System.out.println(names);

        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        //Converter<String,Integer> converter =  Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        int num = 1;
        Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);
        System.out.println(stringConverter.convert(2));

        Predicate<String> predicate = (s) -> s.length() > 0;

        predicate.test("foo");              // true
        predicate.negate().test("foo");     // false
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        backToString.apply("123");     // "123"

        Optional<String> optional = Optional.of("bmw");
        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"


        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        stringCollection.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);
        System.out.println("---------------------------------------------------");
        stringCollection.stream().filter(s -> s.startsWith("a")).sorted().forEach(System.out::println);
        System.out.println(stringCollection);

        boolean anyStartsWithA =
                stringCollection
                        .stream()
                        .anyMatch(s -> s.startsWith("a"));

        System.out.println(anyStartsWithA);      // true

        boolean allStartsWithA =
                stringCollection
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));

        System.out.println(allStartsWithA);      // false

        boolean noneStartsWithZ =
                stringCollection
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));

        System.out.println(noneStartsWithZ);      // true

        long counts = stringCollection.stream().filter(s -> s.startsWith("a")).count();
        System.out.println(counts);

        Optional<String> reduce = stringCollection.stream().sorted().reduce(((s, s2) -> s + "#" + s2));
        System.out.println(reduce);

        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));

        long t2 = System.nanoTime();
        long count1 = values.parallelStream().sorted().count();
        System.out.println(count1);
        long t3 = System.nanoTime();
        long millis1 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
        System.out.println(String.format("parallel sort took: %d ms", millis1));


        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((id, val) -> System.out.println(val));
        map.computeIfPresent(3,(key,val)->val+key);
        System.out.println(map.get(3));

        map.computeIfAbsent(23, a -> "val" + a);
        System.out.println(map.containsKey(23));    // true

        map.computeIfAbsent(3, a -> "bam");
        System.out.println(map.get(3));             // val33

    }
}