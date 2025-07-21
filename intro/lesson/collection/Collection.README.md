![Collection JDK21](https://cr.openjdk.org/~smarks/collections/SequencedCollectionDiagram20220216.png)

# Java Collections and Stream Operations Guide

## Stream Operations: map vs flatMap

### map
- Use when: You need to transform each element into exactly one other element
- Input: Stream<T> → Output: Stream<R>
- Example:
```java
// Converting list of strings to their lengths
List<String> words = List.of("hello", "world");
List<Integer> lengths = words.stream()
    .map(String::length)  // Each string → one integer
    .toList();  // [5, 5]
```

### flatMap
- Use when: You need to transform each element into zero or more elements
- Input: Stream<T> → Output: Stream<R> (flattened)
- Example:
```java
// Split sentences into words
List<String> sentences = List.of("hello world", "java streams");
List<String> words = sentences.stream()
    .flatMap(sentence -> Arrays.stream(sentence.split(" ")))  // Each sentence → multiple words
    .toList();  // ["hello", "world", "java", "streams"]
```

## Collectors

### Collectors.toMap()
```java
Collectors.toMap(keyMapper, valueMapper, mergeFunction)
```
- Use when: Creating a Map from a Stream
- **Gotchas:**
  1. Default implementation is HashMap (no guaranteed order)
  2. Throws IllegalStateException on duplicate keys unless merge function provided
  3. Not null-safe by default

Example with handling duplicates:
```java
Map<Long, Employee> employeeMap = employees.stream()
    .collect(Collectors.toMap(
        Employee::id,           // key mapper
        e -> e,                // value mapper
        (e1, e2) -> e1        // merge function for duplicates
    ));
```

### Collectors.groupingBy()
```java
Collectors.groupingBy(classifier, downstream)
```
- Use when: Grouping elements by a key into collections
- **Gotchas:**
  1. Returns HashMap by default (no order guarantee)
  2. Creates ArrayList for grouped values by default
  3. Downstream collector can change the collection type

Example:
```java
// Group employees by department
Map<String, List<Employee>> byDept = employees.stream()
    .collect(Collectors.groupingBy(
        Employee::department,    // classifier
        Collectors.toList()     // downstream collector
    ));
```

## Common Gotchas and Solutions

### 1. Map Ordering
**Problem:** Need ordered results from toMap()
```java
// Unordered (HashMap)
Collectors.toMap(...)

// Solution 1: Specify LinkedHashMap
Collectors.toMap(
    keyMapper, 
    valueMapper, 
    mergeFunction,
    LinkedHashMap::new
)

// Solution 2: Use TreeMap for natural ordering
Collectors.toMap(
    keyMapper,
    valueMapper,
    mergeFunction,
    TreeMap::new
)
```

### 2. Duplicate Keys
**Problem:** Stream contains duplicate keys when collecting to map
```java
// Throws IllegalStateException
.collect(Collectors.toMap(Employee::id, e -> e))

// Solution: Provide merge function
.collect(Collectors.toMap(
    Employee::id,
    e -> e,
    (existing, replacement) -> existing  // Keep first occurrence
))
```

### 3. Null Values
**Problem:** Stream contains null values
```java
// Throws NullPointerException
.collect(Collectors.toMap(...))

// Solution: Filter nulls or use filtering collector
.filter(Objects::nonNull)
.collect(Collectors.toMap(...))
```

### 4. Complex Grouping
**Problem:** Need custom grouping logic
```java
// Simple grouping
Collectors.groupingBy(Employee::department)

// With downstream aggregation
Collectors.groupingBy(
    Employee::department,
    Collectors.mapping(
        Employee::name,
        Collectors.toList()
    )
)

// With custom collection type
Collectors.groupingBy(
    Employee::department,
    TreeMap::new,          // custom map type
    Collectors.toSet()     // custom collection type
)
```

## Performance Considerations

1. **Parallel Streams:**
   - Don't automatically parallelize everything
   - Best for large datasets with independent operations
   - Consider overhead of splitting and merging

2. **Collection Choice:**
   - ArrayList: Best for random access
   - LinkedList: Best for frequent insertions/deletions
   - HashSet: Best for uniqueness without order
   - LinkedHashSet: Best for uniqueness with insertion order
   - TreeSet: Best for uniqueness with natural/custom ordering

3. **Map Operations:**
   - HashMap: O(1) average case operations
   - TreeMap: O(log n) operations but sorted
   - LinkedHashMap: O(1) operations with ordering

## Java 21 Features

1. **Sequenced Collections:**
   - New interfaces for collections with defined encounter order
   - Uniform API for first/last element access
   - Reverse view support
   - See `SeqCollectionExample.java` for detailed examples

2. **Immutable Collections:**
   - Use `List.of()`, `Set.of()`, `Map.of()` for small collections
   - Use builders for larger immutable collections
   ```java
   var list = List.of(1, 2, 3);  // Immutable
   var map = Map.ofEntries(...);  // Immutable
   ```