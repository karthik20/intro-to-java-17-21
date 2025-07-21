# Functional Programming in Java

## Why Functional Programming?

Functional programming is a programming paradigm that treats computation as the evaluation of mathematical functions and avoids changing state and mutable data. In Java, it enables:

1. **More Readable Code**: Express "what to do" rather than "how to do it"
2. **Thread Safety**: Immutable data and pure functions are inherently thread-safe
3. **Easier Testing**: Pure functions are predictable and easier to test
4. **Better Abstraction**: Higher-order functions allow better code reuse
5. **Parallel Processing**: Immutable data and pure functions make parallel processing safer

## Functional Interfaces

A functional interface is an interface with exactly one abstract method. Java 8 introduced several built-in functional interfaces.

### Basic Functional Interface
```java
@FunctionalInterface
interface Greeting {
    String greet(String name);  // Single abstract method
}

// Traditional anonymous class
Greeting traditional = new Greeting() {
    @Override
    public String greet(String name) {
        return "Hello, " + name;
    }
};

// Lambda expression
Greeting lambda = name -> "Hello, " + name;
```

## Common Functional Interfaces

### 1. Predicate<T>
Used for boolean-valued functions of one argument.

```java
// Definition
@FunctionalInterface
public interface Predicate<T> {
    boolean test(T t);
}

// Example Usage
Predicate<Integer> isEven = num -> num % 2 == 0;
Predicate<String> isLongWord = str -> str.length() > 5;

// Combining predicates
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
List<Integer> evenNumbers = numbers.stream()
    .filter(isEven)
    .toList();

// Predicate chaining
Predicate<Integer> isPositive = num -> num > 0;
Predicate<Integer> isPositiveAndEven = isPositive.and(isEven);
```

### 2. Function<T,R>
Represents a function that accepts one argument and produces a result.

```java
// Definition
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}

// Example Usage
Function<String, Integer> strLength = String::length;
Function<Integer, String> intToString = String::valueOf;

// Function composition
Function<String, String> processor = strLength
    .andThen(intToString)
    .andThen(s -> "Length is: " + s);
```

### 3. BiFunction<T,U,R>
Represents a function that takes two arguments and produces a result.

```java
// Definition
@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
}

// Example Usage (from ApplyStrategySolution.java)
BiFunction<Double, Double, Double> applyDiscount = 
    (price, discountPercent) -> price - (price * discountPercent / 100);

Double finalPrice = applyDiscount.apply(100.0, 10.0); // 90.0
```

### 4. Consumer<T>
Represents an operation that accepts a single input and returns no result.

```java
// Definition
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}

// Example Usage
Consumer<String> printer = System.out::println;
List<String> names = List.of("Alice", "Bob", "Charlie");
names.forEach(printer);

// Consumer chaining
Consumer<String> logger = s -> System.out.println("Logging: " + s);
Consumer<String> printAndLog = printer.andThen(logger);
```

### 5. Supplier<T>
Represents a supplier of results, takes no arguments but produces a value.

```java
// Definition
@FunctionalInterface
public interface Supplier<T> {
    T get();
}

// Example Usage
Supplier<LocalDateTime> currentTime = LocalDateTime::now;
Supplier<Double> randomNumber = Math::random;

// Lazy evaluation
Supplier<ExpensiveObject> lazyLoader = () -> new ExpensiveObject();
ExpensiveObject object = lazyLoader.get(); // Created only when needed
```

## Best Practices

1. **Method References**
   ```java
   // Instead of
   str -> str.toLowerCase()
   // Use
   String::toLowerCase
   ```

2. **Pure Functions**
   ```java
   // Bad: Impure function with side effect
   int counter = 0;
   Function<Integer, Integer> impure = x -> x + counter++;

   // Good: Pure function
   Function<Integer, Integer> pure = x -> x + 1;
   ```

3. **Immutable Data**
   ```java
   // Bad: Mutable list
   List<String> mutableList = new ArrayList<>();

   // Good: Immutable list
   List<String> immutableList = List.of("a", "b", "c");
   ```

## Real-World Examples

### Example 1: Discount Calculator (from ApplyStrategySolution.java)
```java
BiFunction<Double, Double, Double> applyDiscount = 
    (price, discountPercent) -> price - (price * discountPercent / 100);

List<Double> discounts = List.of(10.0, 5.0);
double finalPrice = discounts.stream()
    .reduce(originalPrice, applyDiscount::apply);
```

### Example 2: Data Validation Chain
```java
Predicate<String> notNull = Objects::nonNull;
Predicate<String> notEmpty = str -> !str.trim().isEmpty();
Predicate<String> validLength = str -> str.length() <= 100;

Predicate<String> isValid = notNull
    .and(notEmpty)
    .and(validLength);

boolean isValidInput = isValid.test(userInput);
```

## Benefits of Using Functional Interfaces

1. **Code Reusability**: Functions can be passed as parameters
2. **Composition**: Chain operations together
3. **Lazy Evaluation**: Operations execute only when needed
4. **Parallel Processing**: Easy to parallelize operations
5. **Better Testing**: Pure functions are easier to test

## Common Use Cases

1. **Collection Processing**
   ```java
   list.stream()
       .filter(predicate)
       .map(function)
       .forEach(consumer);
   ```

2. **Event Handling**
   ```java
   button.addActionListener(e -> handleClick());
   ```

3. **Resource Management**
   ```java
   withResource(resource -> processingLogic(resource));
   ```

4. **Validation Chains**
   ```java
   Predicate<Input> validateAll = validateA
       .and(validateB)
       .and(validateC);
   ```

5. **Lazy Initialization**
   ```java
   Supplier<ExpensiveResource> lazyResource = () -> createResource();
   ```
