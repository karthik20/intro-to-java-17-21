package exercise.collection;

import java.util.*;


public class SeqCollectionExample {
    
    // Test data structure - DO NOT MODIFY
    record Task(int id, String description, int priority) {
        @Override
        public String toString() {
            return String.format("[%d] %s (priority: %d)", id, description, priority);
        }
    }

    // Sample data - DO NOT MODIFY
    private static List<Task> createSampleTasks() {
        return List.of(
            new Task(1, "Review code", 2),
            new Task(2, "Write documentation", 3),
            new Task(3, "Fix bugs", 1),
            new Task(4, "Deploy application", 1),
            new Task(5, "Update dependencies", 2)
        );
    }

    /**
     * Exercise 1: Working with SequencedList
     * 
     * Tasks:
     * 1. Create an ArrayList from the sample tasks
     * 2. Add a new "Planning" task (id=0, priority=1) at the beginning
     * 3. Add a "Project Review" task (id=6, priority=3) at the end
     * 4. Remove the first and last tasks
     * 5. Return the remaining tasks in reverse order
     */
    public List<Task> exerciseSequencedList() {
        // TODO: Implement this method
        // Hint: Use addFirst(), addLast(), removeFirst(), removeLast(), reversed()
        return null;
    }

    /**
     * Exercise 2: Working with SequencedSet
     * 
     * Tasks:
     * 1. Create a LinkedHashSet from sample tasks
     * 2. Add two new tasks with id=1 (duplicate id) but different descriptions
     * 3. Return all tasks with priority <= 2 in reverse order
     * 
     * Expected behavior: Demonstrate how sets handle duplicates while maintaining order
     */
    public List<Task> exerciseSequencedSet() {
        // TODO: Implement this method
        // Hint: Use addFirst(), filter by priority, and reversed()
        return null;
    }

    /**
     * Exercise 3: Working with SequencedMap
     * 
     * Tasks:
     * 1. Create a LinkedHashMap with task.id as key and task as value
     * 2. Add a "Start" task at the beginning (id=0)
     * 3. Add an "End" task at the end (id=99)
     * 4. Remove all tasks with priority > 2
     * 5. Return the remaining tasks in original order
     */
    public List<Task> exerciseSequencedMap() {
        // TODO: Implement this method
        // Hint: Use putFirst(), putLast(), sequencedValues()
        return null;
    }

    /**
     * Exercise 4: Advanced Operations
     * 
     * Implement a priority queue using SequencedList that:
     * 1. Maintains tasks in priority order (lowest number = highest priority)
     * 2. When adding tasks, inserts them in the correct position
     * 3. Provides methods to:
     *    - Add a task in priority order
     *    - Remove highest priority task
     *    - View all tasks in priority order
     */
    public class PriorityTaskList {
        private final List<Task> tasks;

        public PriorityTaskList() {
            this.tasks = new ArrayList<>();
        }

        public void addTask(Task task) {
            // TODO: Implement this method
            // Add the task in the correct position based on priority
        }

        public Task removeHighestPriority() {
            // TODO: Implement this method
            // Remove and return the task with highest priority (lowest number)
            return null;
        }

        public List<Task> getAllTasksByPriority() {
            // TODO: Implement this method
            // Return all tasks sorted by priority
            return null;
        }
    }

    /**
     * Exercise 5: Real-world Scenario
     * 
     * Implement a task management system that:
     * 1. Groups tasks by priority using a SequencedMap
     * 2. For each priority, maintains a SequencedSet of tasks
     * 3. Provides methods to:
     *    - Add a task (automatically grouped by priority)
     *    - Get all tasks for a priority level
     *    - Get all tasks grouped by priority in reverse order
     */
    public class TaskManager {
        private final LinkedHashMap<Integer, LinkedHashSet<Task>> tasksByPriority;

        public TaskManager() {
            this.tasksByPriority = new LinkedHashMap<>();
        }

        public void addTask(Task task) {
            // TODO: Implement this method
            // Add task to appropriate priority group
        }

        public Set<Task> getTasksByPriority(int priority) {
            // TODO: Implement this method
            // Return all tasks for given priority
            return null;
        }

        public Map<Integer, Set<Task>> getAllTasksReversed() {
            // TODO: Implement this method
            // Return all tasks grouped by priority in reverse order
            return null;
        }
    }

    /**
     * Bonus Challenge:
     * Implement a method that takes a list of tasks and:
     * 1. Groups them by priority
     * 2. Within each priority, sorts them by id
     * 3. Returns a flattened list in reverse priority order
     * 4. Uses only Sequenced Collection methods (no explicit sorting)
     */
    public List<Task> bonusChallenge(List<Task> tasks) {
        // TODO: Implement this method
        return null;
    }

    // Test your implementations
    public static void main(String[] args) {
        SeqCollectionExample exercises = new SeqCollectionExample();
        
        System.out.println("Exercise 1 - SequencedList Result:");
        System.out.println(exercises.exerciseSequencedList());
        
        System.out.println("\nExercise 2 - SequencedSet Result:");
        System.out.println(exercises.exerciseSequencedSet());
        
        System.out.println("\nExercise 3 - SequencedMap Result:");
        System.out.println(exercises.exerciseSequencedMap());
        
        // Test PriorityTaskList
        PriorityTaskList priorityList = exercises.new PriorityTaskList();
        createSampleTasks().forEach(priorityList::addTask);
        System.out.println("\nPriority Task List:");
        System.out.println(priorityList.getAllTasksByPriority());
        
        // Test TaskManager
        TaskManager manager = exercises.new TaskManager();
        createSampleTasks().forEach(manager::addTask);
        System.out.println("\nTask Manager - Priority 1 Tasks:");
        System.out.println(manager.getTasksByPriority(1));
        System.out.println("\nTask Manager - All Tasks Reversed:");
        System.out.println(manager.getAllTasksReversed());
        
        // Test Bonus Challenge
        System.out.println("\nBonus Challenge Result:");
        System.out.println(exercises.bonusChallenge(createSampleTasks()));
    }
}
