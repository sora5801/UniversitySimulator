package UniversitySimulator.controller;


public class FoodOrderedMessage implements Message{
        String name;

public FoodOrderedMessage(String name) {
        this.name = name;
        }

public String getName() {
        return name;
        }
}
