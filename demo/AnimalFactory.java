// Factory pattern to return different Animal objects
class AnimalFactory {
    public static Animal createAnimal(String type) {
        return switch (type) {
            case "Dog" -> new Dog();
            case "Cat" -> new Cat();
            case "Bird" -> new Bird();
            default -> throw new IllegalArgumentException("Unknown animal type");
        };
    }
}
