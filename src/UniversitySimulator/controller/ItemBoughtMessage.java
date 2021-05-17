package UniversitySimulator.controller;

import java.util.LinkedList;

/**
 * This is the class for the Item Bought message. Everytime
 * the student buys something from the bookstore, this message gets sent to the controller
 *
 */
public class ItemBoughtMessage implements Message {
    LinkedList<String> items;

    /**
     * The constructor for the message.
     * @param items the items bought
     */
    public ItemBoughtMessage(LinkedList<String> items) {
        this.items = items;
    }

    /**
     * Gets the list of items bought.
     * @return list of items
     */
    public LinkedList<String> getItems() {
        return this.items;
    }

    /**
     * Gets all the name of the items.
     * @return name of the items
     */
    @Override
    public String getName() {
        String names = "";
        for (String item : items) {
            names += " " + item;
        }
        return names;
    }
}
