package demo;

// Factory pattern to return different Animal objects
class AnimalFactory {
    public static demo.Animal createAnimal(String type) {
        return switch (type) {
            case "Dog" -> new demo.Dog();
            case "Cat" -> new demo.Cat();
            case "Bird" -> new demo.Bird();
            default -> throw new IllegalArgumentException("Unknown animal type");
        };
    }
}
