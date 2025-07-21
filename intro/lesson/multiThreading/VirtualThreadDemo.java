package lesson.multiThreading;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class VirtualThreadDemo {

    /**
     * Simulates an I/O bound task (like a database query or HTTP request)
     */
    private static String simulateIOOperation(String threadInfo) {
        try {
            // Simulate I/O operation that takes 100ms
            Thread.sleep(100);
            return "Completed I/O operation in " + threadInfo;
        } catch (InterruptedException e) {
            return "Interrupted: " + e.getMessage();
        }
    }

    /**
     * Demonstrates platform threads with a fixed thread pool
     */
    public static void runWithPlatformThreads(int taskCount) {
        System.out.println("\nRunning with Platform Threads (Fixed Pool)...");
        Instant start = Instant.now();

        try (ExecutorService executor = Executors.newFixedThreadPool(20)) {
            IntStream.range(0, taskCount).forEach(i -> {
                executor.submit(() -> {
                    String result = simulateIOOperation(Thread.currentThread().toString());
                    System.out.println(result);
                });
            });

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Duration duration = Duration.between(start, Instant.now());
        System.out.printf("Platform threads completed %d tasks in %d ms%n", 
            taskCount, duration.toMillis());
    }

    /**
     * Demonstrates virtual threads
     */
    public static void runWithVirtualThreads(int taskCount) {
        System.out.println("\nRunning with Virtual Threads...");
        Instant start = Instant.now();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, taskCount).forEach(i -> {
                executor.submit(() -> {
                    String result = simulateIOOperation(Thread.currentThread().toString());
                    System.out.println(result);
                });
            });

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Duration duration = Duration.between(start, Instant.now());
        System.out.printf("Virtual threads completed %d tasks in %d ms%n", 
            taskCount, duration.toMillis());
    }

    /**
     * Demonstrates different ways to create virtual threads
     */
    public static void demonstrateVirtualThreadCreation() {
        System.out.println("\nDemonstrating Virtual Thread Creation Methods...");

        // Method 1: Using Thread.ofVirtual()
        Thread vThread1 = Thread.ofVirtual()
            .name("custom-virtual-1")
            .start(() -> System.out.println("Virtual Thread 1: " + Thread.currentThread()));

        // Method 2: Using Thread.startVirtualThread()
        Thread vThread2 = Thread.startVirtualThread(() -> 
            System.out.println("Virtual Thread 2: " + Thread.currentThread()));

        // Method 3: Using ExecutorService
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> 
                System.out.println("Virtual Thread 3: " + Thread.currentThread()));
        }

        try {
            vThread1.join();
            vThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Main method to run demonstrations
     */
    public static void main(String[] args) {
        System.out.println("=== Virtual Thread vs Platform Thread Demonstration ===");
        
        // First demonstrate different ways to create virtual threads
        demonstrateVirtualThreadCreation();

        // Now compare performance with different number of tasks
        int[] taskCounts = {100, 1000, 10000};
        
        for (int taskCount : taskCounts) {
            System.out.println("\n=== Running " + taskCount + " tasks ===");
            runWithPlatformThreads(taskCount);
            runWithVirtualThreads(taskCount);
        }
        
        System.out.println("\nKey Observations:");
        System.out.println("1. Virtual threads are more efficient for I/O-bound tasks");
        System.out.println("2. Platform threads are limited by system resources");
        System.out.println("3. Virtual threads can handle many more concurrent tasks");
        System.out.println("4. Virtual threads use same programming model as platform threads");
    }
}
