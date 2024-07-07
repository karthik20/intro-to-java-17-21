package lesson.functionalPgm;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConsumerExample {
    public static void main(String[] args) {
        // Intakes a value and processes it
        Consumer<List<String>> consumeMessages = messages -> messages.forEach(s -> System.out.println("Inserting message to DB:: "+ s));
        consumeMessages.accept(List.of("Order 1 received", "Order 5 received"));


        // Creates or Generates
        Supplier<Integer> randomInt = () -> (int)(Math.random() * 1000);
        Supplier<Double> randomDouble = () -> (double)(Math.random() * 1000.00);

        //Check Optional orElseGet api on supplier

        System.out.println(Optional.ofNullable(null).orElse("Empty"));
        System.out.println(Optional.ofNullable(null).orElseGet(randomInt));

        System.out.println(randomInt.get());
        System.out.println(randomDouble.get());

        // How optional uses supplier
    }
}
