package ch6.s2;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Example1 {

    public static void main(String[] args) {
        int num =2;
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.forEach((Integer value) -> System.out.println(value));

        Function<Integer,Integer> stringConverter = (from) -> from * num;
        System.out.println(stringConverter.apply(3));
    }
}
