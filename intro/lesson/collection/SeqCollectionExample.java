package lesson.collection;

import java.util.*;

/**
 * This class demonstrates the usage of Sequenced Collections introduced in Java 21.
 * Sequenced collections provide uniform APIs for accessing elements from both ends
 * and guarantee iteration order.
 */
public class SeqCollectionExample {
    
    // Test data - a record to represent a Task with priority
    record Task(int id, String description, int priority) {
        @Override
        public String toString() {
            return String.format("[%d] %s (priority: %d)", id, description, priority);
        }
    }

    // Sample data creation
    private static List<Task> createSampleTasks() {
        return List.of(
            new Task(1, "Review code", 2),
            new Task(2, "Write documentation", 3),
            new Task(3, "Fix bugs", 1),
            new Task(4, "Deploy application", 1),
            new Task(5, "Update dependencies", 2)
        );
    }

    
    public void demonstrateSequencedList() {
        System.out.println("\n=== Demonstrating SequencedCollection with ArrayList ===");
        
        // Create an ArrayList (implements SequencedList)
        ArrayList<Task> tasks = new ArrayList<>(createSampleTasks());
        
        // Add elements at both ends
        tasks.addFirst(new Task(0, "Plan sprint", 1));
        tasks.addLast(new Task(6, "Retrospective", 3));
        
        System.out.println("All tasks in order:");
        tasks.forEach(System.out::println);
        
        // Get elements from both ends without removing
        System.out.println("\nFirst task: " + tasks.getFirst());
        System.out.println("Last task: " + tasks.getLast());
        
        // Remove elements from both ends
        Task removedFirst = tasks.removeFirst();
        Task removedLast = tasks.removeLast();
        
        System.out.println("\nAfter removing first and last:");
        System.out.println("Removed first: " + removedFirst);
        System.out.println("Removed last: " + removedLast);
        
        // Get reversed view
        System.out.println("\nTasks in reverse order:");
        tasks.reversed().forEach(System.out::println);
    }

    
    public void demonstrateSequencedSet() {
        System.out.println("\n=== Demonstrating SequencedSet with LinkedHashSet ===");
        
        // Create a LinkedHashSet (implements SequencedSet)
        LinkedHashSet<Task> uniqueTasks = new LinkedHashSet<>(createSampleTasks());
        
        System.out.println("First task in set: " + uniqueTasks.getFirst());
        System.out.println("Last task in set: " + uniqueTasks.getLast());
        
        // Add new task at the beginning
        uniqueTasks.addFirst(new Task(0, "New highest priority", 1));
        
        System.out.println("\nTasks after adding new first:");
        uniqueTasks.forEach(System.out::println);
        
        // Demonstrate reversed view with stream operations
        System.out.println("\nHigh priority tasks (priority <= 2) in reverse order:");
        uniqueTasks.reversed()
                  .stream()
                  .filter(task -> task.priority() <= 2)
                  .forEach(System.out::println);
    }

    public void demonstrateSequencedMap() {
        System.out.println("\n=== Demonstrating SequencedMap with LinkedHashMap ===");
        
        // Create a LinkedHashMap (implements SequencedMap)
        LinkedHashMap<Integer, Task> taskMap = new LinkedHashMap<>();
        createSampleTasks().forEach(task -> taskMap.put(task.id(), task));
        
        // Access first and last entries
        System.out.println("First entry: " + taskMap.firstEntry());
        System.out.println("Last entry: " + taskMap.lastEntry());
        
        // Add new entries at the beginning and end
        taskMap.putFirst(0, new Task(0, "Start of day", 1));
        taskMap.putLast(99, new Task(99, "End of day", 3));
        
        // Get sequential views
        System.out.println("\nAll tasks from first to last:");
        taskMap.sequencedValues().forEach(System.out::println);
        
        System.out.println("\nAll tasks from last to first:");
        taskMap.sequencedValues()
              .reversed()
              .forEach(System.out::println);
        
        // Remove and get first/last entries
        System.out.println("\nRemoving first and last entries:");
        System.out.println("Removed first: " + taskMap.pollFirstEntry());
        System.out.println("Removed last: " + taskMap.pollLastEntry());
    }

    
    public static void main(String[] args) {
        SeqCollectionExample demo = new SeqCollectionExample();
        
        // Demonstrate each collection type
        demo.demonstrateSequencedList();
        demo.demonstrateSequencedSet();
        demo.demonstrateSequencedMap();
        
        System.out.println("\n=== When to use Sequenced Collections ===");
        System.out.println("1. When you need ordered data with first/last access");
        System.out.println("2. When you need to process elements in reverse order");
        System.out.println("3. When you need consistent API for bidirectional access");
        System.out.println("4. When maintaining insertion order is important");
    }
}
