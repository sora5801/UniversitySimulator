package UniversitySimulator.controller;

import java.util.LinkedList;

public class ItemBoughtMessage implements Message {
    LinkedList<String> items;

    public ItemBoughtMessage(LinkedList<String> items) {
        this.items = items;
    }

    public LinkedList<String> getItems() {
        return this.items;
    }

    @Override
    public String getName() {
        String names = "";
        for (String item : items) {
            names += " " + item;
        }
        return names;
    }
}
