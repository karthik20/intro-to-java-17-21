package lesson.oops;

// Abstract class demonstrating Abstraction
public abstract class Animal {
    // Data hiding using private access modifier
    private String name;
    private int age;
    
    // Constructor demonstration
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Encapsulation - Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age > 0) {  // Data validation in encapsulation
            this.age = age;
        }
    }
    
    // Abstract method - forcing implementation in child classes
    public abstract String makeSound();
    
    // Method that will be inherited by all subclasses
    public String getInfo() {
        return "Name: " + name + ", Age: " + age;
    }
}
