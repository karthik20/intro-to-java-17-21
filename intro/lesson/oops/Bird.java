package lesson.oops;

// Inheritance - Bird extends Animal
public class Bird extends Animal {
    // Additional attribute specific to Bird
    private boolean canFly;
    
    // Constructor with super() call
    public Bird(String name, int age, boolean canFly) {
        super(name, age);  // Calling parent constructor
        this.canFly = canFly;
    }
    
    // Implementation of abstract method - Polymorphism
    @Override
    public String makeSound() {
        return "Tweet tweet!";
    }
    
    // Additional method specific to Bird
    public boolean isCanFly() {
        return canFly;
    }
    
    // Method overloading - another form of Polymorphism
    public String getInfo() {
        return super.getInfo() + ", Can fly: " + canFly;
    }
}
