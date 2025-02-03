//import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// Main class to demonstrate static analysis targets
public class ComplexTest {

    public static void main(String[] args) {
        // Demonstrating CHA - Static analysis should resolve method calls
        List<Animal> animals = new List<Animal>();
        animals.add(new Dog());
        animals.add(new Cat());
        animals.add(new Bird());

        // Invoking polymorphic methods
        for (Animal animal : animals) {
            animal.makeSound();
            animal.move();
        }

        // Using factory method to introduce dynamic allocation
        Animal randomAnimal = getRandomAnimal();
        randomAnimal.makeSound();
        randomAnimal.move();

        // Testing factory pattern with user input simulation
        Animal factoryAnimal = AnimalFactory.createAnimal("Cat");
        factoryAnimal.makeSound();
        factoryAnimal.move();
    }

    // Method introducing dynamic dispatch complexity for RTA
    public static Animal getRandomAnimal() {
        Random rand = new Random();
        int choice = rand.nextInt(3);
        if (choice == 0) {
            return new Dog();
        } else if (choice == 1) {
            return new Cat();
        } else {
            return new Bird();
        }
    }
}
