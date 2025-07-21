package lesson.collection;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DataStructureExample {

    record Employee(long id, String name, String department) {
    }

    List<Employee> createRandomEmployees() {
        return List.of(
                new Employee(1, "Alice", "Engineering"),
                new Employee(5, "Gg", "Engineering"),
                new Employee(2, "Bob", "Marketing"),
                new Employee(3, "Charlie", "Engineering"),
                new Employee(4, "David", "Sales"),
                new Employee(5, "Eve", "Marketing"),
                new Employee(4, "DVID", "Sales"),
                new Employee(6, "Frank", "HR"),
                new Employee(7, "Grace", "Engineering"),
                new Employee(5, "EVE", "Marketing"),
                new Employee(8, "Heidi", "Sales"),
                new Employee(9, "Ivan", "HR"),
                new Employee(10, "Judy", "Marketing"),
                new Employee(9, "IVAN", "HR"));
    }

    private List<Employee> allEmployees = createRandomEmployees();

    Map<String, List<Employee>> groupEmployeesByDepartment() {
        return null;
    }

    List<Employee> getUniqueEmployeesByIdAsList() {

        // TODO: Will this work?
        var employeesById = allEmployees.stream().collect(Collectors.groupingBy(Employee::id));
        // flatten employeesById to get final list
        employeesById.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return allEmployees.stream()
                .collect(Collectors.toMap(Employee::id, employee -> employee, (e1, e2) -> e1))
                .values()
                .stream()
                // TODO: Should i type it everytime to sort even numbers??
                // Pro tip: Use out of box comparators from primitive classes Long, Int
                .sorted((employee1, employee2) -> {
                    if (employee1.id() == employee2.id()) {
                        return 0;
                    }
                    return employee1.id() < employee2.id() ? -1 : 1;
                // TODO: remove sorted and ket me know if solution is still sorted
                }).toList();
    }

    Set<Integer> getUniqueNumbers() {
        var numbersWithDuplicates = Arrays.asList(1, 3, 2, 1, 2, 4, 5, 3, 5, 7, 9, 23, 3, 6, 8, 34, 6, 50);
        return null;
    }

    Set<Employee> getUniqueEmployeesById() {
        return allEmployees.stream()
                .collect(Collectors.toMap(Employee::id, employee -> employee, (e1, e2) -> e1))
                .values()
                .stream()
                //.sorted((e1, e2) -> Long.compare(e1.id(), e2.id()))
                // .collect(Collectors.toSet());
                .collect(Collectors.toCollection(() -> new LinkedHashSet<>()));
        // TODO: use a set but retain order
        // .collect(Collectors.toCollection(() -> new TreeSet<>((e1, e2) -> Long.compare(e1.id(), e2.id()))));
    }

    public static void main(String[] args) {
        var ds = new DataStructureExample();
        System.out.println(ds.getUniqueEmployeesById());

        
    }
}
