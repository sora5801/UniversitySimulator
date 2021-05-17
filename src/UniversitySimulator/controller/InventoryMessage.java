package UniversitySimulator.controller;

/**
 * This is the inventory message class. It sends the inventory of user to the controller
 */
public class InventoryMessage implements Message{
    String name;

    /**
     * Constructor for the class. This sets up the name of the inventory message.
     * @param str name of inventory
     */
    public InventoryMessage(String str) {
        this.name = str;
    }

    /**
     * The name of the inventory message.
     * @return name of inventory
     */
    public String getName() {
        return name;
    }
}
