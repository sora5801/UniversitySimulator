package UniversitySimulator.controller;

/**
 * This is the class that is called whenever the user does not input a valid input when prompted for food
 */
public class FoodErrorMessage implements Message{
    /**
     * returns the error message.
     * @return
     */
    @Override
    public String getName() {
        return "Sorry, that is not an option on the menu";
    }
}
