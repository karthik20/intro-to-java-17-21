package lesson.functionalPgm;

import java.util.function.Function;


// Strategy pattern
public class Discount {

    // Discount codes for June holidays , 7-7 sale
    static Function<Double, Double> applyJuneDiscount = (price) -> price - (price * 0.05);
    static Function<Double, Double> applySeven7Discount = (price) -> price - (price * 0.05);

    public static void main(String[] args) {
        System.out.println(applyUserDiscount(100.00));
    }

    private static Double applyUserDiscount(Double price) {
        return applyJuneDiscount
                .andThen(applySeven7Discount)
                .apply(price);
    }
}
