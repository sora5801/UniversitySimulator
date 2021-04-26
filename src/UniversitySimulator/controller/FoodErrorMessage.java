package UniversitySimulator.controller;

public class FoodErrorMessage implements Message{
    @Override
    public String getName() {
        return "Sorry, that is not an option on the menu";
    }
}
