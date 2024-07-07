package exercise.functionalPgm;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static exercise.utils.SolutionValidation.assertWithPredicate;

public class ApplyStrategy {

    final static double iphonePrice = 1200.0;
    static final Map<String, List<Double>> usersWithDiscount = Map.of("John", List.of(7.0, 10.0),
            "Jack", List.of(5.0, 5.0),
            "Sparrow", List.of(10.0),
            "Peter", List.of(4.0));

    // Bonus
    private Double getDiscountedPriceForUser(String userName) {
        // Solution available in ApplyStrategySolution
        return 0.0;
    }

    public static void main(String[] args) {

        ApplyStrategy applyStrategy = new ApplyStrategy();
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
