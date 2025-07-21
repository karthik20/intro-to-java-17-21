package lesson.collection;

import java.util.*;

public class SeqCollectionExampleSolution {
    
    // Test data structure
    record Task(int id, String description, int priority) {
        @Override
        public String toString() {
            return String.format("[%d] %s (priority: %d)", id, description, priority);
        }
    }

    // Sample data
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
     * Solution for Exercise 1: Working with SequencedList
     */
    public List<Task> exerciseSequencedList() {
        ArrayList<Task> tasks = new ArrayList<>(createSampleTasks());
        
        // Add tasks at the beginning and end
        tasks.addFirst(new Task(0, "Planning", 1));
        tasks.addLast(new Task(6, "Project Review", 3));
        
        // Remove first and last tasks
        tasks.removeFirst();
        tasks.removeLast();
        
        // Return remaining tasks in reverse order
        return tasks.reversed();
    }

    /**
     * Solution for Exercise 2: Working with SequencedSet
     */
    public List<Task> exerciseSequencedSet() {
        LinkedHashSet<Task> taskSet = new LinkedHashSet<>(createSampleTasks());
        
        // Add tasks with duplicate IDs but different descriptions
        taskSet.addFirst(new Task(1, "Code Review Meeting", 2));
        taskSet.addFirst(new Task(1, "Design Review", 2));
        
        // Filter tasks by priority and convert to list for reverse order
        return taskSet.stream()
                     .filter(task -> task.priority() <= 2)
                     .toList()
                     .reversed();
    }

    /**
     * Solution for Exercise 3: Working with SequencedMap
     */
    public List<Task> exerciseSequencedMap() {
        LinkedHashMap<Integer, Task> taskMap = new LinkedHashMap<>();
        
        // Add initial tasks
        createSampleTasks().forEach(task -> taskMap.put(task.id(), task));
        
        // Add tasks at beginning and end
        taskMap.putFirst(0, new Task(0, "Start", 1));
        taskMap.putLast(99, new Task(99, "End", 3));
        
        // Remove tasks with priority > 2
        taskMap.values().removeIf(task -> task.priority() > 2);
        
        // Return remaining tasks in original order
        return new ArrayList<>(taskMap.sequencedValues());
    }

    /**
     * Solution for Exercise 4: Advanced Operations
     */
    public class PriorityTaskList {
        private final List<Task> tasks;

        public PriorityTaskList() {
            this.tasks = new ArrayList<>();
        }

        public void addTask(Task task) {
            // Find the correct position to insert based on priority
            int insertIndex = 0;
            for (Task t : tasks) {
                if (task.priority() >= t.priority()) {
                    insertIndex++;
                } else {
                    break;
                }
            }
            tasks.add(insertIndex, task);
        }

        public Task removeHighestPriority() {
            return tasks.isEmpty() ? null : tasks.removeFirst();
        }

        public List<Task> getAllTasksByPriority() {
            return new ArrayList<>(tasks);
        }
    }

    /**
     * Solution for Exercise 5: Real-world Scenario
     */
    public class TaskManager {
        private final LinkedHashMap<Integer, LinkedHashSet<Task>> tasksByPriority;

        public TaskManager() {
            this.tasksByPriority = new LinkedHashMap<>();
        }

        public void addTask(Task task) {
            tasksByPriority.computeIfAbsent(task.priority(), k -> new LinkedHashSet<>())
                          .add(task);
        }

        public Set<Task> getTasksByPriority(int priority) {
            return tasksByPriority.getOrDefault(priority, new LinkedHashSet<>());
        }

        public Map<Integer, Set<Task>> getAllTasksReversed() {
            LinkedHashMap<Integer, Set<Task>> reversed = new LinkedHashMap<>();
            tasksByPriority.reversed().forEach((priority, tasks) -> 
                reversed.put(priority, new LinkedHashSet<>(tasks)));
            return reversed;
        }
    }

    /**
     * Solution for Bonus Challenge
     */
    public List<Task> bonusChallenge(List<Task> tasks) {
        // Create a map to group tasks by priority
        LinkedHashMap<Integer, LinkedHashSet<Task>> groupedTasks = new LinkedHashMap<>();
        
        // Group tasks by priority and sort by ID within each group
        tasks.forEach(task -> {
            groupedTasks.computeIfAbsent(task.priority(), k -> new LinkedHashSet<>())
                       .add(task);
        });
        
        // Create result list in reverse priority order
        ArrayList<Task> result = new ArrayList<>();
        groupedTasks.reversed().forEach((priority, taskSet) -> 
            result.addAll(taskSet));
            
        return result;
    }

    // Test the solutions
    public static void main(String[] args) {
        SeqCollectionExampleSolution solution = new SeqCollectionExampleSolution();
        
        System.out.println("Exercise 1 - SequencedList Solution:");
        System.out.println(solution.exerciseSequencedList());
        
        System.out.println("\nExercise 2 - SequencedSet Solution:");
        System.out.println(solution.exerciseSequencedSet());
        
        System.out.println("\nExercise 3 - SequencedMap Solution:");
        System.out.println(solution.exerciseSequencedMap());
        
        // Test PriorityTaskList
        PriorityTaskList priorityList = solution.new PriorityTaskList();
        createSampleTasks().forEach(priorityList::addTask);
        System.out.println("\nPriority Task List Solution:");
        System.out.println("All tasks by priority:");
        System.out.println(priorityList.getAllTasksByPriority());
        System.out.println("Removing highest priority tasks:");
        System.out.println(priorityList.removeHighestPriority());
        System.out.println(priorityList.removeHighestPriority());
        
        // Test TaskManager
        TaskManager manager = solution.new TaskManager();
        createSampleTasks().forEach(manager::addTask);
        System.out.println("\nTask Manager Solution:");
        System.out.println("Tasks with priority 1:");
        System.out.println(manager.getTasksByPriority(1));
        System.out.println("All tasks in reverse priority order:");
        System.out.println(manager.getAllTasksReversed());
        
        // Test Bonus Challenge
        System.out.println("\nBonus Challenge Solution:");
        System.out.println(solution.bonusChallenge(createSampleTasks()));
    }
}
