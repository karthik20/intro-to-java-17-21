package exercise.utils;

import java.util.function.Predicate;

public class SolutionValidation {

    final static String CORRECT = "\033[0;32m" + "✅";
    final static String WRONG = "\033[0;31m" + "❌";
    public static <T> void assertWithPredicate(String testCaseName, T input, Predicate<T> validator) {
        if (!validator.test(input)) {
            var error = """ 
                    %s :: Test case failed. %s does not match the expected value
                    """.formatted(testCaseName, input);
            System.err.println(WRONG + error);
        } else {
            System.out.println(CORRECT + testCaseName + ":: Test case passed!");
        }
        //System.out.println("\n");
    }
}
