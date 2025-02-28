// Base interface for CHA and RTA analysis
public interface Animal {
    void makeSound();
    void move();

    default void beAlive() {
        makeSound();
        move();
    }
}
