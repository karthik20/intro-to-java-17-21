package lesson.oops;

// Another concrete class extending Animal
public class Dog extends Animal {
    private String breed;
    
    // Constructor
    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }
    
    // Implementation of abstract method - Polymorphism
    @Override
    public String makeSound() {
        return "Woof woof!";
    }
    
    public String getBreed() {
        return breed;
    }
    
    @Override
    public String getInfo() {
        return super.getInfo() + ", Breed: " + breed;
    }
}
