package lesson.functionalPgm;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionalPgm {

    public static void main(String[] args) {

        Flyable<String> flyable = () -> "I'm flying..";
        System.out.println(flyable.fly());

        Function<Double, Double> square = num -> num * num;
        BiFunction<String, Double, Double> squareWithMessage = (msg, num) -> {
            System.out.println(msg);
            return num * num;
        };
        System.out.println(square.apply(5.0));
        System.out.println(squareWithMessage.apply("Squaring",5.0));

        System.out.println(square.andThen(square).apply(5.0));

        // System.out.println(square(square(5.0)));
        // why not use direct method calls?

        // Dynamically apply functionality
    }

//    private static double square(double num) {
//        return num * num;
//    }
}
