package lesson.functionalPgm;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// use Java Predicate
public class Filter {

    public static void main(String[] args) {
        var numbers = IntStream.rangeClosed(0, 10).boxed().toList();
        System.out.println(filterEvenNumbers(numbers));
        System.out.println(filterOddNumbers(numbers));
        //System.out.println(filterByCondition(numbers, n -> n % 3 == 0));
    }

    private static List<Integer> filterEvenNumbers(List<Integer> numbers) {
        // return numbers.stream().filter(num -> num % 2 == 0).toList();
        return null;
    }

    private static List<Integer> filterOddNumbers(List<Integer> numbers) {
        // return numbers.stream().filter(num -> num % 2 != 0).toList();
        return null;
    }
}
