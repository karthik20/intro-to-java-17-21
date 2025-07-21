package exercise.collection;

import static exercise.utils.SolutionValidation.assertWithPredicate;
import java.util.*;

public class HeroStructure {

    record Department(long id, String name) {
    }

    record Hero(Long id, String fname, String lname, List<Department> departments) {
    }

    Map<String, List<Hero>> groupHeroesByDepartmentName() {
        return null;
    }

    Set<String> getUniqueDepartmentNames() {
        return null;
    }

    List<Hero> findHeroesByFirstName(String fname) {
        return null;
    }

    public static void main(String[] args) {
        // implement methods such that
        HeroStructure heroStructure = new HeroStructure();
        System.out.println("\n");
        assertWithPredicate("findHeroesByFirstName",
                heroStructure.findHeroesByFirstName("Peter"),
                (expected) -> Objects.nonNull(expected) && expected.containsAll(List.of(peter, pan)));
        assertWithPredicate("getUniqueDepartments", heroStructure.getUniqueDepartmentNames(),
                (expected) -> Objects.nonNull(expected)
                        && expected.size() == (new HashSet<>(allDepartments)).size()
                        && expected.containsAll(
                        List.of(avengers.name(), highTable.name(), justiceLeague.name(),
                                dailyBugle.name(), starkIndustries.name(),
                                dailyPlanet.name(), disney.name())));

        assertWithPredicate("groupHeroesByDepartmentName",
                heroStructure.groupHeroesByDepartmentName(),
                (expected) -> Objects.nonNull(expected)
                        && expected.keySet()
                        .containsAll(Set.of(avengers.name(), highTable.name(), justiceLeague.name(),
                                dailyBugle.name(), starkIndustries.name(),
                                dailyPlanet.name(), disney.name())));

    }

    // Creates a list of heroes with different departments
    static final Department avengers = new Department(1, "Avengers");
    static final Department highTable = new Department(2, "High Table");
    static final Department justiceLeague = new Department(3, "Justice League");
    static final Department dailyBugle = new Department(4, "Daily Bugle");
    static final Department starkIndustries = new Department(5, "Stark Industries");
    static final Department dailyPlanet = new Department(6, "Daily Planet");
    static final Department disney = new Department(7, "Disney");
    static final List<Department> allDepartments = List.of(avengers, highTable, justiceLeague, dailyBugle, starkIndustries, dailyPlanet, disney);

    static final Hero john = new Hero(1L, "John", "Wick", List.of(highTable));
    static final Hero peter = new Hero(2L, "Peter", "Parker", List.of(dailyBugle, avengers));
    static final Hero tony = new Hero(3L, "Tony", "Stark", List.of(avengers, starkIndustries));
    static final Hero clark = new Hero(4L, "Clark", "Kent", List.of(dailyPlanet, justiceLeague));
    static final Hero pan = new Hero(5L, "Peter", "Pan", List.of(disney));
    static final Hero diana = new Hero(6L, "Diana", "Prince", List.of(justiceLeague));
    static final List<Hero> allHeroes = List.of(john, peter, tony, clark, pan, diana);
}
