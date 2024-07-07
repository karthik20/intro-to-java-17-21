package exercise.collection;

import java.util.*;

import static exercise.utils.SolutionValidation.assertWithPredicate;

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
    static Department avengers = new Department(1, "Avengers");
    static Department highTable = new Department(2, "High Table");
    static Department justiceLeague = new Department(3, "Justice League");
    static Department dailyBugle = new Department(4, "Daily Bugle");
    static Department starkIndustries = new Department(5, "Stark Industries");
    static Department dailyPlanet = new Department(6, "Daily Planet");
    static Department disney = new Department(7, "Disney");
    static List<Department> allDepartments = List.of(avengers, highTable, justiceLeague, dailyBugle, starkIndustries, dailyPlanet, disney);

    static Hero john = new Hero(1L, "John", "Wick", List.of(highTable));
    static Hero peter = new Hero(2L, "Peter", "Parker", List.of(dailyBugle, avengers));
    static Hero tony = new Hero(3L, "Tony", "Stark", List.of(avengers, starkIndustries));
    static Hero clark = new Hero(4L, "Clark", "Kent", List.of(dailyPlanet, justiceLeague));
    static Hero pan = new Hero(5L, "Peter", "Pan", List.of(disney));
    static Hero diana = new Hero(6L, "Diana", "Prince", List.of(justiceLeague));
    static List<Hero> allHeroes = List.of(john, peter, tony, clark, pan, diana);
}
