package UniversitySimulator.controller;

/**
 * This is the message that is called everytime the user orders something
 */
public class FoodOrderedMessage implements Message{
        String name;

        /**
         * Constructs the class with the name of the food.
         * @param name
         */
        public FoodOrderedMessage(String name) {
        this.name = name;
        }

        /**
         * Gets the name of the food
         * @return
         */
        public String getName() {
        return name;
        }
}
