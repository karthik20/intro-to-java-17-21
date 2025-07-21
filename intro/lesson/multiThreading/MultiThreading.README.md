# Java Threading and Concurrency

## Evolution of Threading in Java

### 1. Traditional Threading (Pre-Java 8)
```java
// Method 1: Extending Thread
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}
// Usage: new MyThread().start();

// Method 2: Implementing Runnable
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Runnable running: " + Thread.currentThread().getName());
    }
}
// Usage: new Thread(new MyRunnable()).start();
```

### 2. Modern Threading (Java 8+)
```java
// Lambda with Runnable
Runnable task = () -> System.out.println("Modern thread: " + Thread.currentThread().getName());
new Thread(task).start();
```

### 3. Virtual Threads (Java 21+)
```java
// Creating a virtual thread
Thread vThread = Thread.ofVirtual().start(() -> 
    System.out.println("Virtual thread: " + Thread.currentThread().getName())
);

// Or using structured concurrency
try (var scope = StructuredTaskScope.ShutdownOnFailure()) {
    scope.fork(() -> computeTask());
    scope.join();
}
```

## ExecutorService Framework

### Types of Executors
1. **Fixed Thread Pool**
   ```java
   ExecutorService fixed = Executors.newFixedThreadPool(5);
   ```
   - Fixed number of threads
   - Good for CPU-bound tasks
   - Threads are reused

2. **Cached Thread Pool**
   ```java
   ExecutorService cached = Executors.newCachedThreadPool();
   ```
   - Creates new threads as needed
   - Reuses idle threads
   - Good for many short-lived tasks

3. **Single Thread Executor**
   ```java
   ExecutorService single = Executors.newSingleThreadExecutor();
   ```
   - Single thread for sequential tasks
   - Guaranteed order of execution

4. **Virtual Thread Per Task Executor (Java 21)**
   ```java
   ExecutorService virtual = Executors.newVirtualThreadPerTaskExecutor();
   ```
   - Creates virtual thread for each task
   - Extremely lightweight
   - Good for I/O-bound tasks

### execute() vs submit()

1. **execute(Runnable)**
   - Void return type
   - No way to track task completion
   - Fire and forget
   ```java
   executorService.execute(() -> System.out.println("Task executed"));
   ```

2. **submit(Runnable/Callable)**
   - Returns Future object
   - Can track task completion
   - Can get result or exception
   ```java
   Future<String> future = executorService.submit(() -> "Task result");
   ```

### Runnable vs Callable

1. **Runnable**
   - No return value
   - Cannot throw checked exceptions
   ```java
   Runnable task = () -> System.out.println("Simple task");
   ```

2. **Callable**
   - Returns a value
   - Can throw checked exceptions
   ```java
   Callable<String> task = () -> {
       if (error) throw new Exception("Task failed");
       return "Task completed";
   };
   ```

## Future and CompletableFuture

### Future
- Basic async result container
- Blocking operations
```java
Future<String> future = executor.submit(callable);
String result = future.get(1, TimeUnit.SECONDS); // Blocks
```

### CompletableFuture
- Non-blocking operations
- Chain operations
- Handle errors gracefully
```java
CompletableFuture<String> cf = CompletableFuture
    .supplyAsync(() -> "Step 1")
    .thenApply(s -> s + " -> Step 2")
    .thenAccept(System.out::println);
```

## Virtual Threads (Java 21)

### Key Benefits
1. **Lightweight**: Millions of virtual threads possible
2. **Efficient**: Automatic scheduling on platform threads
3. **Simple**: Same programming model as platform threads

### Best Use Cases
1. Server applications handling many concurrent connections
2. I/O intensive applications
3. Applications with many blocking operations

### Gotchas
1. **Thread Local Storage**: Be cautious with thread locals
2. **Pinning**: Avoid thread-pinning operations
3. **Monitoring**: Traditional thread monitoring tools might need updates

```java
// Bad: Thread pinning
synchronized(lock) {
    Thread.sleep(1000); // Virtual thread stays pinned
}

// Good: Avoid pinning
lock.lock();
try {
    Thread.sleep(1000);
} finally {
    lock.unlock();
}
```

## Thread Control Methods

### 1. Thread.join()
- Waits for thread to die
- Useful for thread coordination
- Can specify timeout
```java
Thread thread = new Thread(() -> heavyTask());
thread.start();
try {
    // Wait for thread to complete or timeout after 5 seconds
    thread.join(5000);
    if (thread.isAlive()) {
        System.out.println("Thread didn't finish in time!");
    }
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

### 2. Thread.sleep()
- Pauses current thread execution
- Releases CPU but holds monitors/locks
- Always catches InterruptedException
```java
try {
    // Sleep for 1 second
    Thread.sleep(1000);
    
    // Sleep with nanos precision
    Thread.sleep(1, 500000); // 1 second and 500,000 nanoseconds
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

### 3. Thread.yield()
- Hints to scheduler to yield CPU
- No guarantee of effect
- Rarely used in practice
```java
while (processing) {
    if (lowPriority) {
        Thread.yield(); // Let other threads have a chance
    }
    // Continue processing
}
```

### 4. Thread States and Transitions
```java
Thread thread = new Thread(() -> task());
System.out.println(thread.getState()); // NEW

thread.start();
System.out.println(thread.getState()); // RUNNABLE

// After sleep() -> TIMED_WAITING
// After join() -> WAITING
// In synchronized block -> BLOCKED
// After completion -> TERMINATED
```

### 5. Thread Interruption
- Cooperative cancellation mechanism
- Doesn't force thread to stop
- Common pattern for clean shutdown
```java
Thread task = new Thread(() -> {
    while (!Thread.currentThread().isInterrupted()) {
        try {
            // Do work
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Restore interrupted status and break
            Thread.currentThread().interrupt();
            break;
        }
    }
});

task.start();
// Later...
task.interrupt(); // Request cancellation
```

### Common Gotchas with Thread Control

1. **Sleep Holding Locks**
```java
// Bad: Holds lock while sleeping
synchronized(lock) {
    Thread.sleep(1000); // Other threads can't access lock
}

// Good: Release lock before sleeping
synchronized(lock) {
    // Do minimal work
}
Thread.sleep(1000);
```

2. **Join Without Timeout**
```java
// Risky: Could wait forever
thread.join();

// Better: Use timeout
if (!thread.join(timeout)) {
    // Handle timeout case
}
```

3. **Ignoring InterruptedException**
```java
// Bad: Swallowing interruption
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {} // Don't do this!

// Good: Restore interrupt status
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

4. **Yield Instead of Proper Synchronization**
```java
// Bad: Using yield for synchronization
while (!ready) {
    Thread.yield(); // Unreliable
}

// Good: Use proper synchronization
synchronized(lock) {
    while (!ready) {
        lock.wait();
    }
}
```

## Best Practices

1. **Thread Pool Sizing**
   - CPU-bound tasks: Number of cores + 1
   - I/O-bound tasks: Higher counts or virtual threads

2. **Resource Management**
   ```java
   try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
       // Use executor
   } // Auto-shutdown
   ```

3. **Error Handling**
   ```java
   CompletableFuture<String> cf = CompletableFuture
       .supplyAsync(() -> "task")
       .exceptionally(ex -> "fallback");
   ```

4. **Shutdown Properly**
   ```java
   executor.shutdown();
   if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
       executor.shutdownNow();
   }
   ```

## ForkJoin Framework and Structured Concurrency

### ForkJoinPool
A specialized ExecutorService for divide-and-conquer tasks. It implements work-stealing where idle threads can steal tasks from busy threads.

```java
class SumTask extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 10_000;

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeDirectly();
        }
        
        int mid = start + length / 2;
        SumTask leftTask = new SumTask(numbers, start, mid);
        SumTask rightTask = new SumTask(numbers, mid, end);
        
        leftTask.fork();  // Submit new subtask to pool
        long rightResult = rightTask.compute(); // Do this task yourself
        long leftResult = leftTask.join();  // Wait for forked task
        
        return leftResult + rightResult;
    }
}

// Usage
ForkJoinPool pool = ForkJoinPool.commonPool();
long result = pool.invoke(new SumTask(numbers, 0, numbers.length));
```

### Structured Concurrency (Java 21)
Manages the lifetime of threads within a scope, ensuring all subtasks complete or fail together.

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    // Fork two tasks
    Future<String> user = scope.fork(() -> fetchUser(userId));
    Future<List<Order>> orders = scope.fork(() -> fetchOrders(userId));
    
    // Wait for all tasks to complete or first failure
    scope.join();
    // Throws if any task failed
    scope.throwIfFailed();
    
    // Both tasks completed successfully
    return new UserData(user.resultNow(), orders.resultNow());
}
```

### Common Patterns
1. **ShutdownOnFailure**
```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    scope.fork(() -> criticalTask());
    scope.fork(() -> anotherCriticalTask());
    scope.join();           // Wait for completion
    scope.throwIfFailed();  // Propagate any failure
}
```

2. **ShutdownOnSuccess**
```java
try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
    scope.fork(() -> slowServer());
    scope.fork(() -> fastServer());
    return scope.join().result(); // Returns first successful result
}
```

## Understanding Concurrency: A Kitchen Metaphor 🍳

> Imagine a busy restaurant kitchen during peak hours...

### 1. volatile ⚡
Picture a whiteboard where orders are written:
- The whiteboard shows current orders (like a `volatile` variable)
- All chefs can see updates immediately (direct memory access)
- But chefs might grab same ingredients simultaneously (no coordination)
- Great for status flags, not for complex operations

```java
volatile boolean isKitchenOpen = true;  // Like a "Kitchen Open" sign
```

### 2. synchronized 🔒
Think of a walk-in pantry with one key:
- Only one chef can enter at a time (exclusive access)
- Other chefs must wait their turn (thread blocking)
- Ensures ingredients aren't double-used (data consistency)
- But can slow down kitchen if overused (performance impact)

```java
synchronized void getPantryIngredients() {  // Like locking the pantry
    // Only one chef here at a time
}
```

### 3. AtomicInteger 🔄
Imagine a modern order numbering system:
- Digital counter that can't show wrong numbers (atomic operations)
- Chefs don't need to wait to get next number (non-blocking)
- Perfect for quick, independent operations (like order counting)
- But limited to simple operations (can't handle complex tasks)

```java
AtomicInteger orderNumber = new AtomicInteger(0);  // Like a digital order counter
```

## Memory Synchronization and CPU Locking

### ReentrantLock
More flexible alternative to synchronized, with additional features:

```java
public class AdvancedCounter {
    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;
    
    public void increment() {
        lock.lock();  // Explicit locking
        try {
            count++;
        } finally {
            lock.unlock();  // Must unlock in finally block
        }
    }
    
    // Trylock with timeout
    public boolean incrementWithTimeout() {
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    count++;
                    return true;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
```

#### ReentrantLock Features
1. **Fairness Option**
```java
// Create a fair lock - first come, first served
ReentrantLock fairLock = new ReentrantLock(true);
```

2. **Lock Polling**
```java
if (lock.tryLock()) {  // Non-blocking attempt
    try {
        // Got the lock
    } finally {
        lock.unlock();
    }
} else {
    // Do something else
}
```

3. **Interruptible Locking**
```java
try {
    lock.lockInterruptibly();
    try {
        // Critical section
    } finally {
        lock.unlock();
    }
} catch (InterruptedException e) {
    // Handle interruption
}
```

4. **Lock Conditions**
```java
ReentrantLock lock = new ReentrantLock();
Condition notFull = lock.newCondition();
Condition notEmpty = lock.newCondition();

// Producer
lock.lock();
try {
    while (isFull()) {
        notFull.await();  // Wait for space
    }
    // Add item
    notEmpty.signal();  // Signal consumer
} finally {
    lock.unlock();
}
```

#### When to Use ReentrantLock over synchronized
1. Need for timeout on lock attempts
2. Need for interruptible lock attempts
3. Need for fair locking
4. Need for multiple conditions
5. Need to check lock status

```java
// Complex locking scenario
class Buffer {
    private final ReentrantLock lock = new ReentrantLock(true);  // Fair lock
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    
    public void put(Object x) throws InterruptedException {
        lock.lockInterruptibly();  // Can be interrupted
        try {
            while (isFull()) {
                notFull.await(1, TimeUnit.SECONDS);  // Timeout
            }
            // Add to buffer
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }
}
```

### volatile Keyword
Ensures visibility of changes across threads by forcing reads/writes directly to main memory.

```java
public class SharedFlag {
    private volatile boolean flag = false;  // Changes visible to all threads
    
    public void toggle() {
        flag = !flag;  // Write directly to main memory
    }
    
    public boolean isSet() {
        return flag;  // Read directly from main memory
    }
}
```

**What happens at CPU level:**
1. Prevents CPU register caching of the variable
2. Establishes happens-before relationship
3. Forces memory barrier instructions
4. May impact performance due to memory fence operations

### synchronized Keyword
Provides exclusive access to a block of code or method through intrinsic locks (monitors).

```java
public class Counter {
    private int count = 0;
    
    // Method-level synchronization
    synchronized void increment() {
        count++;
    }
    
    // Block-level synchronization
    void incrementBlock() {
        synchronized(this) {
            count++;
        }
    }
}
```

**What happens at CPU level:**
1. Acquires monitor/lock (CPU atomic operation)
2. Flushes CPU caches
3. Establishes memory barrier
4. Creates a happens-before relationship
5. May cause thread contention and context switches

### Lock Implementation Details

1. **Uncontended Synchronization**
```java
synchronized(obj) {  // Fast path
    // First attempt uses biased locking
    // If successful, no atomic operations needed
}
```

2. **Contended Synchronization**
```java
synchronized(obj) {  // Slow path
    // 1. Try biased locking
    // 2. If failed, try light-weight locking (spin lock)
    // 3. If still failed, heavy-weight locking (OS mutex)
}
```

### Best Practices for Locking

1. **Fine-grained Locking**
```java
// Bad: Course-grained locking
synchronized void processList(List<Task> tasks) {
    for(Task task : tasks) {
        processTask(task);  // Holds lock too long
    }
}

// Better: Fine-grained locking
void processList(List<Task> tasks) {
    for(Task task : tasks) {
        synchronized(task) {
            processTask(task);  // Lock only what's needed
        }
    }
}
```

2. **Avoid Nested Synchronization**
```java
// Risky: Potential deadlock
synchronized(lockA) {
    synchronized(lockB) {  // Nested locks
        // Work
    }
}

// Better: Use lock ordering or lock combinations
private final Object combinedLock = new Object();
synchronized(combinedLock) {
    // Work with both A and B
}
```

## Performance Considerations

1. **Platform Threads**
   - Memory: ~1MB stack size each
   - Limited by system resources
   - Good for CPU-intensive tasks

2. **Virtual Threads**
   - Memory: ~1KB each
   - Limited mainly by heap size
   - Excellent for I/O-bound tasks
   - Poor for CPU-intensive tasks

3. **Thread Pools**
   - Fixed pools: Predictable resource usage
   - Cached pools: Dynamic but can grow unbounded
   - Virtual threads: No pooling needed
