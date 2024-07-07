package lesson.functionalPgm;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static exercise.utils.SolutionValidation.assertWithPredicate;

public class ApplyStrategySolution {

    final static double iphonePrice = 1200.0;
    static final Map<String, List<Double>> usersWithDiscount = Map.of("John", List.of(7.0, 10.0),
            "Jack", List.of(5.0, 5.0),
            "Sparrow", List.of(10.0),
            "Peter", List.of(4.0));

    private Double getDiscountedPriceForUser(String userName) {
        BiFunction<Double, Double, Double> applyDiscount = (price, discountPercent) -> price - (price * discountPercent / 100);
        List<Double> userDisc = usersWithDiscount.getOrDefault(userName, List.of(0.0));
        return usersWithDiscount.getOrDefault(userName, List.of(0.0))
                .stream()
                .reduce(iphonePrice, applyDiscount::apply);
    }

    public static void main(String[] args) {

        ApplyStrategySolution applyStrategy = new ApplyStrategySolution();
        assertWithPredicate("getDiscountedPriceForUser-John", applyStrategy.getDiscountedPriceForUser("John"),
                discount -> discount.equals(1004.40));
        assertWithPredicate("getDiscountedPriceForUser-Jack", applyStrategy.getDiscountedPriceForUser("Jack"),
                discount -> discount.equals(1083.00));
        assertWithPredicate("getDiscountedPriceForUser-Sparrow", applyStrategy.getDiscountedPriceForUser("Sparrow"),
                discount -> discount.equals(1080.00));
        assertWithPredicate("getDiscountedPriceForUser-Peter", applyStrategy.getDiscountedPriceForUser("Peter"),
                discount -> discount.equals(1152.00));

    }

}
